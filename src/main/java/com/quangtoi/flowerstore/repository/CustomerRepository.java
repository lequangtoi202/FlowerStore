package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.CartItem;
import com.quangtoi.flowerstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
