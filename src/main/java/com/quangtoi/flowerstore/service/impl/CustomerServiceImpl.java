package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.CustomerDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Customer;
import com.quangtoi.flowerstore.repository.AccountRepository;
import com.quangtoi.flowerstore.repository.CustomerRepository;
import com.quangtoi.flowerstore.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private ModelMapper mapper;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public CustomerDto postProfile(CustomerDto customerDto, Account account) {
        Customer customerExisted = account.getCustomer();
        if (customerExisted == null){
            Customer customer = new Customer();
            customer.setAddress(customerDto.getAddress());
            customer.setEmail(customerDto.getEmail());
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            customer.setDob(customerDto.getDob());
            customer.setPhoneNumber(customerDto.getPhoneNumber());

            Customer customerSaved = customerRepository.save(customer);
            account.setCustomer(customerSaved);
            accountRepository.save(account);
            return mapper.map(customerSaved, CustomerDto.class);
        }else{
            return null;
        }


    }

    @Override
    public CustomerDto getProfile(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return mapper.map(customer, CustomerDto.class);
    }

    @Override
    public CustomerDto updateProfileById(CustomerDto customerDto, Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        customer.setAddress(customerDto.getAddress());
        customer.setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setDob(customerDto.getDob());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        Customer customerSaved = customerRepository.save(customer);

        return mapper.map(customerSaved, CustomerDto.class);
    }

    @Override
    public CustomerDto updateProfileByCurrentAccount(CustomerDto customerDto, Account account) {
        Customer customer = account.getCustomer();
        customer.setAddress(customerDto.getAddress());
        customer.setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setDob(customerDto.getDob());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        Customer customerSaved = customerRepository.save(customer);

        return mapper.map(customerSaved, CustomerDto.class);
    }

    @Override
    public void deleteProfile(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        customerRepository.delete(customer);
    }
}
