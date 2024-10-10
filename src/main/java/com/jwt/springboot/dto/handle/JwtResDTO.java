package com.jwt.springboot.dto.handle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtResDTO {

    private String token;
    private String type;
    private String username;
    private String email;
    private List<String> roles;

}
