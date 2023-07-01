package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.LoginDto;
import com.quangtoi.flowerstore.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto) throws Exception;

    String register(RegisterDto registerDto);
}
