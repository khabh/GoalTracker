package com.goaltracker.security.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.auth.util.AuthorityConverter;
import com.goaltracker.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
        com.goaltracker.user.domain.User user = userRepository.findByEmail(principal).orElseThrow();
        UserCredential userCredential = user.getUserCredential();

        return User.withUsername(user.getUsername())
                .password(userCredential.getHashedPassword())
                .authorities(AuthorityConverter.toGrantedAuthorities(userCredential))
                .build();
    }
}
