package com.jwt.springboot.dto;

import com.jwt.springboot.enums.RoleEnum;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RoleDTO {

    private UUID roleId;
    private RoleEnum name;

}
