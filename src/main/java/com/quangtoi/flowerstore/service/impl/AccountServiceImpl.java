package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.AccountDto;
import com.quangtoi.flowerstore.dto.RoleDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Role;
import com.quangtoi.flowerstore.repository.AccountRepository;
import com.quangtoi.flowerstore.repository.RoleRepository;
import com.quangtoi.flowerstore.service.AccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private ModelMapper mapper;
    private AccountRepository accRepo;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;

    @Override
    public AccountDto changePassword(Account account, String newPassword) {
        Account currentAcc = accRepo.findById(account.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", account.getId()));
        currentAcc.setPassword(encoder.encode(newPassword));
        Account accountSaved = accRepo.save(currentAcc);
        return mapper.map(accountSaved, AccountDto.class);
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto, Long id) {
        Account currentAcc = accRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        currentAcc.setName(accountDto.getName());
        currentAcc.setEmail(accountDto.getEmail());
        currentAcc.setUrlAvatar(accountDto.getUrlAvatar());
        Account accountSaved = accRepo.save(currentAcc);
        return mapper.map(accountSaved, AccountDto.class);
    }

    @Override
    public AccountDto addRoleToAccount(Account account, RoleDto roleDto) {
        Account accountSaved = accRepo.findById(account.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", account.getId()));
        Set<Role> roles = accountSaved.getRoles();
        Role role = roleRepository.findByName(roleDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleDto.getName()));;
        System.out.println(roleDto);
        roles.add(role);
        accountSaved.setRoles(roles);
        Account accountUpdated = accRepo.save(accountSaved);
        return mapper.map(accountUpdated, AccountDto.class);
    }

    @Override
    public AccountDto getAccount(Long id) {
        Account account = accRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));

        return mapper.map(account, AccountDto.class);
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accRepo.findByUsername(username).get();
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accRepo.findAll()
                .stream()
                .map(acc -> mapper.map(acc, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateResetPassword(String token, String email) {
        Account account = accRepo.findByEmail(email).get();
        if (account != null){
            account.setResetPasswordToken(token);
            accRepo.save(account);
        }
        else{
            throw new ResourceNotFoundException("Account", "email", email);
        }
    }

    @Override
    public Account getByResetPasswordToken(String resetPasswordToken) {
        return accRepo.findByResetPasswordToken(resetPasswordToken);
    }

    @Override
    public AccountDto getMyAccount(String username) {
        return accRepo.findAccountByUsername(username).get();
    }

}
