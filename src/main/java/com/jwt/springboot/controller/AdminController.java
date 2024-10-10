package com.jwt.springboot.controller;

import com.jwt.springboot.dto.UserDTO;
import com.jwt.springboot.dto.handle.ResponseDTO;
import com.jwt.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    @PostMapping(value = "/get-user")
    public ResponseDTO<List<UserDTO>> getUser(@RequestBody UserDTO userDTO) {
        return userService.getUser();
    }

    @PostMapping(value = "/create-user")
    public ResponseDTO<UserDTO> userServiceResponseDTO (@RequestBody UserDTO request) {
        return userService.createUser(request);
    }

}
