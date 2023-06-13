package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.OrderDetailDto;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDto> getOrderDetailByOrderId(Long id);
}
