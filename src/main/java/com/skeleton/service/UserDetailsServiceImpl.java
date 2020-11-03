package com.skeleton.service;


import com.skeleton.database.UserRepository;
import com.skeleton.model.User;
import com.skeleton.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User securityUser = userRepository.findByUsername(s).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with name %s does not exist", s)));

        return withUsername(securityUser.getUsername())
                .password(securityUser.getPassword())
                .authorities(securityUser.getUserRoles().stream().map(UserRole::getRole).collect(Collectors.toList()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public String username() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
