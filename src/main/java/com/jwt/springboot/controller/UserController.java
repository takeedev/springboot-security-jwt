package com.jwt.springboot.controller;

import com.jwt.springboot.dto.UserDTO;
import com.jwt.springboot.dto.handle.ResponseDTO;
import com.jwt.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/view-data")
    public ResponseDTO<UserDTO> userServiceResponseDTO (@RequestBody UserDTO request) {
        return userService.createUser(request);
    }

    @PostMapping(value = "/get-user")
    public ResponseDTO<List<UserDTO>> getUser(@RequestBody UserDTO userDTO) {
        return userService.getUser();
    }

}
