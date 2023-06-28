package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.AccountDto;
import com.quangtoi.flowerstore.dto.LoginDto;
import com.quangtoi.flowerstore.dto.RegisterDto;
import com.quangtoi.flowerstore.exception.FlowerApiException;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Cart;
import com.quangtoi.flowerstore.model.Customer;
import com.quangtoi.flowerstore.model.Role;
import com.quangtoi.flowerstore.repository.AccountRepository;
import com.quangtoi.flowerstore.repository.RoleRepository;
import com.quangtoi.flowerstore.security.CustomUserDetailsService;
import com.quangtoi.flowerstore.security.JwtTokenProvider;
import com.quangtoi.flowerstore.service.AuthService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;
    private AccountRepository accRepo;
    private ModelMapper mapper;
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    @Override
    public String login(LoginDto loginDto) {

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDto.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        String token = tokenProvider.generateToken(usernamePasswordAuthenticationToken);
        return token;
    }



    @Override
    public String register(RegisterDto registerDto) {
        if (accRepo.existsByUsername(registerDto.getUsername())){
            throw new FlowerApiException(HttpStatus.BAD_REQUEST, "Username is already exist");
        }

        if (accRepo.existsByEmail(registerDto.getEmail())){
            throw new FlowerApiException(HttpStatus.BAD_REQUEST, "Email is already exist");
        }
        Account account = new Account();
        account.setEmail(registerDto.getEmail());
        account.setName(registerDto.getName());
        account.setUsername(registerDto.getUsername());
        account.setCart(new Cart());
        account.setCustomer(new Customer());
        account.setCreatedAt(LocalDateTime.now());
        account.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepo.findByName("ROLE_USER").get();
        roles.add(userRole);
        account.setRoles(roles);
        accRepo.save(account);

        return "User registered successfully";
    }
}
