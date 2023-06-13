package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.dto.CustomerDto;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.service.AccountService;
import com.quangtoi.flowerstore.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
@CrossOrigin
public class CustomerController {
    private CustomerService customerService;
    private AccountService accountService;

    @Operation(summary = "Update customer info")
    @PostMapping("/profile")
    public ResponseEntity<CustomerDto> postProfile(@RequestBody CustomerDto customerDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Account account = accountService.getAccountByUsername(username);
                if (account != null) {
                    return new ResponseEntity<>(customerService.postProfile(customerDto, account), HttpStatus.CREATED);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //test ok
    @Operation(summary = "Get profile of customer by id")
    @GetMapping("/profile/{id}")
    public ResponseEntity<CustomerDto> profile(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(customerService.getProfile(id));
    }

    //test ok
    @Operation(summary = "Update profile by curent account")
    @PutMapping("/profile")
    public ResponseEntity<CustomerDto> updateProfileByCurrentAccount(@RequestBody CustomerDto customerDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Account account = accountService.getAccountByUsername(username);
                if (account != null) {
                    return ResponseEntity.ok().body(customerService.updateProfileByCurrentAccount(customerDto, account));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //test ok
    @Operation(summary = "Update profile by id")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("/profile/{id}")
    public ResponseEntity<CustomerDto> updateProfileById(@RequestBody CustomerDto customerDto,
                                                         @PathVariable("id") Long id){
        return ResponseEntity.ok().body(customerService.updateProfileById(customerDto, id));
    }

    //test ok nhưng ko đc xóa do constraint
    @Operation(summary = "Delete profile REST API ( hạn chế dùng)")
    @DeleteMapping("/profile/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable("id") Long id){
        customerService.deleteProfile(id);
        return ResponseEntity.ok().body("Delete profile successfully!");
    }
}
