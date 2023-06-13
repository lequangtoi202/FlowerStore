package com.quangtoi.flowerstore.service;


import com.quangtoi.flowerstore.dto.OrderDto;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Cart;
import com.quangtoi.flowerstore.model.Customer;
import com.quangtoi.flowerstore.model.Status;

import java.util.List;

public interface OrderService {
    OrderDto saveOrder(Cart cart, Customer customer);
    OrderDto updateOrder(Long id, Status status);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(Long id);
    List<OrderDto> getAllOrdersByAccount(Account account);
}
