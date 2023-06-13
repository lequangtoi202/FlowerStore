package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.CustomerDto;
import com.quangtoi.flowerstore.model.Account;

public interface CustomerService {
    CustomerDto postProfile(CustomerDto customerDto, Account account);
    CustomerDto getProfile(Long id);
    CustomerDto updateProfileById(CustomerDto customerDto, Long id);
    CustomerDto updateProfileByCurrentAccount(CustomerDto customerDto, Account account);
    void deleteProfile(Long id);
}
