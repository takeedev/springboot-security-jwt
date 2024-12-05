package com.jwt.springboot.service.security;

import com.jwt.springboot.dao.UserDAO;
import com.jwt.springboot.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO result = usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found With Username : " + username));
        if (!result.isActive()) {
            throw new DisabledException("Account is not active");
        }
        return UserDetail.build(result);
    }

    public void increaseFailedAttempts(String username) {
        UserDAO result = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        result.setFailedAttempt(result.getFailedAttempt() + 1);
        if (result.getFailedAttempt() >= 3) {
            result.setActive(false);
            result.setDateLocked(new Date());
        }
        usersRepository.save(result);
    }

    public void resetFailedAttempts(String username) {
        UserDAO user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setFailedAttempt(0);
        user.setActive(true);
        user.setDateLocked(null);
        usersRepository.save(user);
    }
}
