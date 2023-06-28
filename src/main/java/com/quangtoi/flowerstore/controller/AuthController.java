package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.dto.JwtAuthResponse;
import com.quangtoi.flowerstore.dto.LoginDto;
import com.quangtoi.flowerstore.dto.RegisterDto;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.service.AccountService;
import com.quangtoi.flowerstore.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {
    private AuthService authService;

    //test ok
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse authResponse = new JwtAuthResponse();
        authResponse.setAccessToken(token);

        return ResponseEntity.ok().body(authResponse);
    }

    //test ok
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return ResponseEntity.ok().body(response);
    }
}
