package com.jwt.springboot.service;

import com.jwt.springboot.dto.UserDTO;
import com.jwt.springboot.dto.handle.ResponseDTO;

import java.util.List;

public interface UserService {

    ResponseDTO<List<UserDTO>> getUser();

    ResponseDTO<UserDTO> createUser(UserDTO userDTO);

    boolean checkEmail(String email);

    boolean checkUsername(String username);

}
