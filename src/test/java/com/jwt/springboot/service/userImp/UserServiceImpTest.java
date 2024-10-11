package com.jwt.springboot.service.userImp;

import com.jwt.springboot.dao.UserDAO;
import com.jwt.springboot.dto.RoleDTO;
import com.jwt.springboot.dto.UserDTO;
import com.jwt.springboot.dto.handle.ResponseDTO;
import com.jwt.springboot.enums.RoleEnum;
import com.jwt.springboot.repository.UsersRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceImpTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserServiceImp userServiceImp;

    @Container
    private static final MSSQLServerContainer<?> SQLSERVER_CONTAINER = new MSSQLServerContainer<>(
            "mcr.microsoft.com/mssql/server:2022-latest")
            .withInitScript("init.sql")
            .acceptLicense();

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", SQLSERVER_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", SQLSERVER_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", SQLSERVER_CONTAINER::getPassword);
    }
    static {
        SQLSERVER_CONTAINER.start();
    }

    @AfterAll
    static void stopContainer(){
        SQLSERVER_CONTAINER.stop();
    }

    @Test
    @DisplayName("should find admin user in user list")
    void shouldFindAdminUserInUserList() {
        List<UserDAO> result = usersRepository.findAll();
        boolean exits = result.stream().anyMatch(e -> e.getUsername().equals("admin"));
        Assertions.assertTrue(exits, "Admin user should exist");
    }

    @Test
    @DisplayName("Should find admin in call service")
    void shouldFindUserIsSuccess() {
        ResponseDTO<List<UserDTO>> result = userServiceImp.getUser();
        boolean exits = result.getData().stream().anyMatch(e -> e.getUsername().equals("admin"));
        Assertions.assertTrue(exits, "Admin user should exist");
    }

    @Test
    @DisplayName("Should add user is success")
    void shouldCreateUserIsSuccess() {
        RoleDTO roleDTO = RoleDTO.builder()
                .name(RoleEnum.ROLE_USER)
                .build();

        Set<RoleDTO> roleDTOS;
        roleDTOS = new HashSet<>();
        roleDTOS.add(roleDTO);

        UserDTO userDTO = UserDTO.builder()
                .username("User1")
                .password(passwordEncoder.encode("password"))
                .email("user@email.com")
                .active(true)
                .createBy("admin")
                .createDate(new Date())
                .role(roleDTOS)
                .build();

        ResponseDTO<UserDTO> result = userServiceImp.createUser(userDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("User1", result.getData().getUsername());

        System.out.println(passwordEncoder.encode("password"));
    }

    @Test
    @DisplayName("should get email is true")
    void shouldGetEmailIsTrue() {
        boolean result = userServiceImp.checkEmail("admin@gmail.com");
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("should get username is true")
    void shouldGetUsernameIsTrue() {
        boolean result = userServiceImp.checkUsername("admin");
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("should get username is false")
    void shouldGetUsernameIsFalse() {
        boolean result = userServiceImp.checkUsername("username");
        Assertions.assertFalse(result);
    }

}