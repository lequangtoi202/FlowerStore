package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.CartDto;
import com.quangtoi.flowerstore.dto.CartItemDto;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Flower;

import java.util.List;

public interface CartService {
    CartDto addItemToCart(Flower flower, int quantity, Account account);
    CartDto updateItemInCart(Flower flower, int quantity, Account account);
    CartDto deleteItemInCart(Flower flower, Account account);
    List<CartItemDto> getCartItems(Long cartId);
}
