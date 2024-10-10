package com.jwt.springboot.controller;

import com.jwt.springboot.config.jwt.JwtTokenUtil;
import com.jwt.springboot.dto.handle.JwtResDTO;
import com.jwt.springboot.dto.request.LoginReqDTO;
import com.jwt.springboot.dto.handle.ResponseDTO;
import com.jwt.springboot.service.UserService;
import com.jwt.springboot.service.security.UserDetail;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseDTO<JwtResDTO> login(@RequestBody LoginReqDTO loginReqDTO) {

        boolean checkUsername = userService.checkUsername(loginReqDTO.getUsername());
        if (!checkUsername) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginReqDTO.getUsername(), loginReqDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        List<String> roles = userDetail.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String jwt = jwtTokenUtil.generateKey(loginReqDTO.getUsername(),roles);
        ResponseDTO<JwtResDTO> userDTOResponseDTO = new ResponseDTO<>();
        userDTOResponseDTO.setData(new JwtResDTO(jwt, "Bearer", userDetail.getUsername(), "admin@gmail.com", roles));
        userDTOResponseDTO.setMessage("login success");
        return userDTOResponseDTO;
    }

}
