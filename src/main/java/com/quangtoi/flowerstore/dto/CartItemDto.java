package com.quangtoi.flowerstore.dto;

import com.quangtoi.flowerstore.model.Cart;
import com.quangtoi.flowerstore.model.Flower;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private int quantity;
    private double totalPrice;
    private Long cartId;
    private Long flowerId;

}
