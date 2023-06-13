package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByAccount(Account account);
}
