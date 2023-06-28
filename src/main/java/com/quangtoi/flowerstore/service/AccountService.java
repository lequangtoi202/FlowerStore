package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.AccountDto;
import com.quangtoi.flowerstore.dto.RoleDto;
import com.quangtoi.flowerstore.model.Account;

import java.util.List;

public interface AccountService {
    AccountDto changePassword(Account account, String newPassword);
    AccountDto updateAccount(AccountDto accountDto, Long id);
    AccountDto addRoleToAccount(Account account, RoleDto roleDto);
    AccountDto getAccount(Long id);
    Account getAccountByUsername(String username);
    List<AccountDto> getAllAccounts();
    void updateResetPassword(String token, String email);
    Account getByResetPasswordToken(String resetPasswordToken);
    AccountDto getMyAccount(String username);
}
