package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.dto.CartDto;
import com.quangtoi.flowerstore.dto.CartItemDto;
import com.quangtoi.flowerstore.dto.FlowerDto;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Cart;
import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.service.AccountService;
import com.quangtoi.flowerstore.service.CartService;
import com.quangtoi.flowerstore.service.FlowerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cart")
@CrossOrigin
public class CartController {
    private AccountService accountService;
    private CartService cartService;
    private FlowerService flowerService;
    private ModelMapper mapper;

    @Operation(
            summary = "get my cart REST API"
    )
    @GetMapping("/me")
    public ResponseEntity<CartDto> getMyCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Account account = accountService.getAccountByUsername(username);
                if (account != null) {
                    Cart cart = account.getCart();
                    return ResponseEntity.ok().body(mapper.map(cart, CartDto.class));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Operation(
            summary = "get cart details REST API"
    )
    @GetMapping("/details")
    public ResponseEntity<List<CartItemDto>> getCartDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Account account = accountService.getAccountByUsername(username);
                if (account != null) {
                    Cart cart = account.getCart();
                    return ResponseEntity.ok().body(cartService.getCartItems(cart.getId()));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    //test ok
    @Operation(
            summary = "add item to cart REST API"
    )
    @PostMapping
    private ResponseEntity<CartDto> addItemToCart(@RequestParam("id") Long id, @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Account account = accountService.getAccountByUsername(username);
                if (account != null) {
                    FlowerDto flowerDto = flowerService.getFlowerById(id);
                    Flower flower = mapper.map(flowerDto, Flower.class);

                    CartDto cartDto = cartService.addItemToCart(flower, quantity, account);
                    return ResponseEntity.ok().body(cartDto);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //test ok
    @Operation(
            summary = "update item to cart REST API"
    )
    @PutMapping("/update-item-cart")
    private ResponseEntity<CartDto> updateItemToCart(@RequestParam("id") Long id,
                                                     @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Account account = accountService.getAccountByUsername(username);
                if (account != null) {
                    FlowerDto flowerDto = flowerService.getFlowerById(id);
                    Flower flower = mapper.map(flowerDto, Flower.class);
                    CartDto cartDto = cartService.updateItemInCart(flower, quantity, account);
                    return ResponseEntity.ok().body(cartDto);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    //test ok
    @Operation(
            summary = "delete item to cart REST API"
    )
    @DeleteMapping("/delete-item-cart")
    private ResponseEntity<CartDto> deleteItemToCart(@RequestParam("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Account account = accountService.getAccountByUsername(username);
                if (account != null) {
                    FlowerDto flowerDto = flowerService.getFlowerById(id);
                    Flower flower = mapper.map(flowerDto, Flower.class);
                    CartDto cartDto = cartService.deleteItemInCart(flower, account);
                    return ResponseEntity.ok().body(cartDto);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
