package com.jwt.springboot.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private boolean active;
    private String createBy;
    private Date createDate;
    private Set<RoleDTO> role;
}
