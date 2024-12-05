package com.jwt.springboot.controller;

import com.jwt.springboot.config.jwt.JwtTokenUtil;
import com.jwt.springboot.dto.handle.JwtResDTO;
import com.jwt.springboot.dto.handle.ResponseDTO;
import com.jwt.springboot.dto.request.LoginReqDTO;
import com.jwt.springboot.enums.StatusCode;
import com.jwt.springboot.service.UserService;
import com.jwt.springboot.service.security.UserDetail;
import com.jwt.springboot.service.security.UserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private final UserDetailService userDetailService;

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDTO<JwtResDTO>> login(@RequestBody LoginReqDTO loginReqDTO) {
        String username = loginReqDTO.getUsername();
        boolean checkUsername = userService.checkUsername(username);
        if (!checkUsername) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<JwtResDTO>builder()
                    .data(null)
                    .message("User not found")
                    .statusCode(StatusCode.CODE_1001)
                    .build());
        }
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, loginReqDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();
            List<String> roles = userDetail.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            String jwt = jwtTokenUtil.generateKey(username,roles);
            userDetailService.resetFailedAttempts(username);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<JwtResDTO>builder()
                    .data(new JwtResDTO(jwt, "Bearer", userDetail.getUsername(), userDetail.getEmail(), roles))
                    .message("Login success")
                    .statusCode(StatusCode.CODE_1001)
                    .build());

        } catch (BadCredentialsException e) {
            userDetailService.increaseFailedAttempts(username);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<JwtResDTO>builder()
                    .data(null)
                    .message("Username or password is correct")
                    .statusCode(StatusCode.CODE_1001)
                    .build());
        }
    }

}
