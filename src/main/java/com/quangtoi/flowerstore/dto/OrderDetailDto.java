package com.quangtoi.flowerstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long id;
    private int quantity;
    private double subTotal;
    private Long orderId;
    private Long flowerId;
}
