package com.jwt.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.springboot.dto.UserDTO;
import com.jwt.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO userDTO;
    private List<UserDTO> userList;

    @BeforeEach
    public void setUp() {
        userDTO = UserDTO.builder()
                .username("testuser")
                .password("password")
                .active(true)
                .build();

        userList = Arrays.asList(userDTO);
    }

}