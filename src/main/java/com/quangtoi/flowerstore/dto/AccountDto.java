package com.quangtoi.flowerstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AccountDto {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String urlAvatar;
    private Long customerId;
    private Long cartId;
    private LocalDateTime createdAt;
}
