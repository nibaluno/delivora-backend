package org.example.lr7.controller;

import org.example.lr7.config.JwtProvider;
import org.example.lr7.model.entity.CartEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.model.enums.USER_ROLE;
import org.example.lr7.repository.CartRepository;
import org.example.lr7.repository.UserRepository;
import org.example.lr7.request.LoginRequest;
import org.example.lr7.response.AuthResponse;
import org.example.lr7.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //лучше через конструктор
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/singup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserEntity user) throws Exception {

        UserEntity isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist != null){
            throw new Exception("Email is already used with another account");

        }
        UserEntity createdUser = new UserEntity();
        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity savedUser = userRepository.save(createdUser);

        CartEntity cart = new CartEntity();
        cart.setId(savedUser.getId());////////
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("login success");

        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    @PostMapping("/singup")
    public ResponseEntity<AuthResponse> singin(@RequestBody LoginRequest req) throws Exception {
        String usename = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(usename, password);
        return null;
    }

    private Authentication authenticate(String usename, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(usename);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username...");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
