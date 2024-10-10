package com.jwt.springboot.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserDAO {

    @Id
    @UuidGenerator
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "active")
    private boolean active;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_date")
    private Date createDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "role")
    private Set<RoleDAO> role = new HashSet<>();

}
