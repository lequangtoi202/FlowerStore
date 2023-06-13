package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.LoginDto;
import com.quangtoi.flowerstore.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
