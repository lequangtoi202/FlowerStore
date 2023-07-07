package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> getCartItemByCartId(Long id);
}
