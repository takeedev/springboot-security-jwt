package com.jwt.springboot.service.security;

import com.jwt.springboot.dao.UserDAO;
import com.jwt.springboot.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO result = usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found With Username : " + username));
        return UserDetail.build(result);
    }
}
