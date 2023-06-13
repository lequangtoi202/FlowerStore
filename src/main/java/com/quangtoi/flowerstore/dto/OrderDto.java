package com.quangtoi.flowerstore.dto;

import com.quangtoi.flowerstore.model.Customer;
import com.quangtoi.flowerstore.model.OrderDetail;
import com.quangtoi.flowerstore.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Date orderDate;
    private Date deliveryDate;
    private Status orderStatus;
    private int totalAmount;
    private double totalPrice;
    private Long customerId;
    private LocalDateTime createdAt;
}
