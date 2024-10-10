package com.jwt.springboot.service.userImp;

import com.jwt.springboot.dao.UserDAO;
import com.jwt.springboot.dto.RoleDTO;
import com.jwt.springboot.dto.UserDTO;
import com.jwt.springboot.dto.handle.ResponseDTO;
import com.jwt.springboot.enums.StatusCode;
import com.jwt.springboot.enums.StatusDescription;
import com.jwt.springboot.repository.UsersRepository;
import com.jwt.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO<List<UserDTO>> getUser() {
        List<UserDAO> result = usersRepository.findAll();
        List<UserDTO> moveToDto = Optional.ofNullable(result).orElse(Collections.emptyList())
                .stream().map(e -> UserDTO.builder()
                        .id(e.getId())
                        .username(e.getUsername())
                        .email(e.getEmail())
                        .password("-")
                        .active(e.isActive())
                        .createBy(e.getCreateBy())
                        .createDate(e.getCreateDate())
                        .role(Optional.ofNullable(e.getRole())
                                .orElse(Collections.emptySet())
                                .stream()
                                .map(roleDAO -> RoleDTO.builder()
                                        .name(roleDAO.getName())
                                        .roleId(roleDAO.getRoleId())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build())
                .collect(Collectors.toList());
        return ResponseDTO.<List<UserDTO>>builder()
                .data(moveToDto)
                .message(StatusDescription.SUCCESS)
                .statusCode(StatusCode.CODE_1001)
                .build();
    }

    @Override
    public ResponseDTO<UserDTO> createUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO newUserDTO = Optional.ofNullable(userDTO)
                .map(dto -> {
                    UserDAO userDAO = new UserDAO();
                    BeanUtils.copyProperties(dto, userDAO);
                    return userDAO;
                })
                .map(usersRepository::save)
                .map(savedUserDAO -> {
                    UserDTO userDtoResult = new UserDTO();
                    BeanUtils.copyProperties(savedUserDAO, userDtoResult);
                    return userDtoResult;
                })
                .orElseThrow(() -> new RuntimeException(StatusDescription.ERROR_CREATE_USER_FAILED));
        return ResponseDTO.<UserDTO>builder()
                .data(newUserDTO)
                .statusCode(StatusCode.CODE_1001)
                .message(StatusDescription.CREATE_USER_SUCCESS)
                .build();
    }

    @Override
    public boolean checkEmail(String email) {
        String result = usersRepository.findByEmail(email);
        return Optional.ofNullable(result)
                .filter(e -> !e.isEmpty())
                .isPresent();
    }

    @Override
    public boolean checkUsername(String username) {
        Optional<UserDAO> result = usersRepository.findByUsername(username);
        return Optional.ofNullable(result)
                .filter(Optional::isPresent)
                .isPresent();
    }

}
