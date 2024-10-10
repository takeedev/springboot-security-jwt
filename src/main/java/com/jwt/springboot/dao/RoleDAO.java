package com.jwt.springboot.dao;

import com.jwt.springboot.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleDAO {

    @Id
    @UuidGenerator
    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

}
