package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.OrderDetailDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Order;
import com.quangtoi.flowerstore.model.OrderDetail;
import com.quangtoi.flowerstore.repository.OrderDetailRepository;
import com.quangtoi.flowerstore.repository.OrderRepository;
import com.quangtoi.flowerstore.service.OrderDetailService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private ModelMapper mapper;
    @Override
    public List<OrderDetailDto> getOrderDetailByOrderId(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order id", id));
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder(order);

        return orderDetails.stream()
                .map(od -> mapper.map(od, OrderDetailDto.class))
                .collect(Collectors.toList());
    }
}
