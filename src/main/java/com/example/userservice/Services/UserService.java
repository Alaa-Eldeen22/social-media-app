package com.example.userservice.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.userservice.Entities.User;
import com.example.userservice.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userRes = userRepository.findByUsername(username);

        if (userRes.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = userRes.get();

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

}
