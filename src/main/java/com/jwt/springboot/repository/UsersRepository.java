package com.jwt.springboot.repository;

import com.jwt.springboot.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UserDAO, UUID> {

    Optional<UserDAO> findByUsername(String username);

    @Query(value = "SELECT email FROM users WHERE email = :email", nativeQuery = true)
    String findByEmail(@Param("email") String email);

}
