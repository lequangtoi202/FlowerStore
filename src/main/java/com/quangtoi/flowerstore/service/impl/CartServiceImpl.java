package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.CartDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Cart;
import com.quangtoi.flowerstore.model.CartItem;
import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.repository.AccountRepository;
import com.quangtoi.flowerstore.repository.CartItemRepository;
import com.quangtoi.flowerstore.repository.CartRepository;
import com.quangtoi.flowerstore.service.CartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private ModelMapper mapper;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(Flower flower, int quantity, Account account) {
        Cart cart = account.getCart();
        if (cart == null){
            cart = new Cart();
        }
        Set<CartItem> cartItems = cart.getCartItems();

        CartItem cartItem = findFlowerInCartItem(cartItems, flower.getId());
        System.out.println(flower);
        if (cartItems.isEmpty()){
            cartItems = new HashSet<>();
            if(cartItem == null){
                cartItem = new CartItem();
                cartItem.setFlower(flower);
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice((Math.round(quantity * flower.getUnitPrice()*100.0)) / 100.0);
                cartItem.setCart(cart);

                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }
        }else{
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setFlower(flower);
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice((Math.round(quantity * flower.getUnitPrice()*100.0)) / 100.0);
                cartItem.setCart(cart);

                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }else{
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + ((Math.round(quantity * flower.getUnitPrice()*100.0)) / 100.0));
                cartItemRepository.save(cartItem);
            }

        }
        cart.setCartItems(cartItems);
        cart.setAccount(account);
        cart.setTotalAmount(totalItems(cartItems));
        cart.setTotalPrice(totalPrices(cartItems));

        Cart cartSaved = cartRepository.save(cart);

        return mapper.map(cartSaved, CartDto.class);
    }

    @Override
    public CartDto updateItemInCart(Flower flower, int quantity, Account account) {
        Cart cart = account.getCart();

        Set<CartItem> cartItems = cart.getCartItems();

        CartItem cartItem = findFlowerInCartItem(cartItems, flower.getId());

        if (cartItem != null){
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice((Math.round(quantity * flower.getUnitPrice()*100.0)) / 100.0);
            cartItemRepository.save(cartItem);
        }

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrices(cartItems);

        cart.setTotalAmount(totalItems);
        cart.setTotalPrice(totalPrice);

        Cart cartSaved = cartRepository.save(cart);
        return mapper.map(cartSaved, CartDto.class);
    }

    @Override
    public CartDto deleteItemInCart(Flower flower, Account account) {
        Cart cart = account.getCart();

        Set<CartItem> cartItems = cart.getCartItems();

        CartItem cartItem = findFlowerInCartItem(cartItems, flower.getId());

        if (cartItem != null){
            cartItems.remove(cartItem);
            cartItemRepository.delete(cartItem);
        }

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrices(cartItems);

        cart.setTotalAmount(totalItems);
        cart.setTotalPrice(totalPrice);

        Cart cartSaved = cartRepository.save(cart);
        return mapper.map(cartSaved, CartDto.class);
    }

    private CartItem findFlowerInCartItem(Set<CartItem> cartItems, Long flowerId){
        if (cartItems == null){
            return null;
        }
        CartItem cartItem = cartItems.stream()
                .filter(item -> item.getFlower().getId().equals(flowerId))
                .collect(Collectors.toSet()).stream()
                .findFirst()
                .orElse(null);

        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems){
        return cartItems
                .stream()
                .map(CartItem::getQuantity)
                .reduce(0, (a, b) -> a+b);
    }

    private double totalPrices(Set<CartItem> cartItems){
        return cartItems
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(0.0, (a, b) -> a+b);
    }

}
