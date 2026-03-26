package com.josebastardo.task_manager.Service;

import com.josebastardo.task_manager.Repository.UserRepository;
import com.josebastardo.task_manager.dto.AuthResponse;
import com.josebastardo.task_manager.dto.LoginRequest;
import com.josebastardo.task_manager.dto.RegisterRequest;
import com.josebastardo.task_manager.model.User;
import com.josebastardo.task_manager.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder= passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse Register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        userRepository.save(user);
        AuthResponse authResponse = new AuthResponse();
        String token = jwtService.generateToken(user);
        authResponse.setToken(token);
        return authResponse;
    }

    public AuthResponse Login(LoginRequest request){
        User user;
        String email;

        if(userRepository.existsByUsername(request.getUsername())){
            System.out.println("Test Username");
            user = userRepository.findByUsername(request.getUsername()).orElse(null);
            email = user.getEmail();
        } else if (userRepository.existsByEmail(request.getUsername())){
            System.out.println("Test Email");
            user = userRepository.findByEmail(request.getUsername()).orElse(null);
            email = request.getUsername();
        }
        else {
            throw new RuntimeException("User not found");
        }

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.getPassword()
                )
        );

        AuthResponse authResponse = new AuthResponse();
        String token = jwtService.generateToken(user);
        authResponse.setToken(token);
        return authResponse;
    }

}
