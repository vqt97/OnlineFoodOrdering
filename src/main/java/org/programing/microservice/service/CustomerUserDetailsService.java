package org.programing.microservice.service;

import org.programing.microservice.model.USER_ROLE;
import org.programing.microservice.model.User;
import org.programing.microservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user==null) {
            throw new UsernameNotFoundException("user not found with email " + username);
        }

        USER_ROLE role = user.getRole();
        if (role==null)
            role = USER_ROLE.ROLE_CUSTOMER;

        List<GrantedAuthority> authoritites = new ArrayList<>();
        authoritites.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), authoritites);
    }
}
