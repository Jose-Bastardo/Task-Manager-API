package com.josebastardo.task_manager.Controller;

import com.josebastardo.task_manager.Service.AuthService;
import com.josebastardo.task_manager.dto.AuthResponse;
import com.josebastardo.task_manager.dto.LoginRequest;
import com.josebastardo.task_manager.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("register") public AuthResponse register(@Valid @RequestBody RegisterRequest request){
        return authService.Register(request);
    }

    @PostMapping("login") public AuthResponse login(@Valid @RequestBody LoginRequest request){
        return authService.Login(request);
    }
}
