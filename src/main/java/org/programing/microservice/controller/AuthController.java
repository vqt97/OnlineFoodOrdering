package org.programing.microservice.controller;

import lombok.RequiredArgsConstructor;
import org.programing.microservice.config.JwtProvider;
import org.programing.microservice.model.Cart;
import org.programing.microservice.model.User;
import org.programing.microservice.repositories.CartRepository;
import org.programing.microservice.repositories.UserRepository;
import org.programing.microservice.response.AuthResponse;
import org.programing.microservice.service.CustomerUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final CartRepository cartRepository;

    @GetMapping
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist!=null)
            throw new Exception("Email is already used with another account");

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User saveUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(saveUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(saveUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}
