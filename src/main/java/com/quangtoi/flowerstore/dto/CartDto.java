package com.quangtoi.flowerstore.dto;

import com.quangtoi.flowerstore.model.Account;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;
    private int totalAmount;
    private double totalPrice;
    private Long accountId;
}
