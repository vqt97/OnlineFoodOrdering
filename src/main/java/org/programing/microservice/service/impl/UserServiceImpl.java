package org.programing.microservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.programing.microservice.config.JwtProvider;
import org.programing.microservice.model.User;
import org.programing.microservice.repositories.UserRepository;
import org.programing.microservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user==null)
            throw new Exception("User not found");

        return user;
    }
}
