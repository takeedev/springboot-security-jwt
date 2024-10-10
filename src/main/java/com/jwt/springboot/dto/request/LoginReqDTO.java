package com.jwt.springboot.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginReqDTO {

    private String username;
    private String password;

}
