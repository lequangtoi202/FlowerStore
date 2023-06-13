package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.OrderDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.*;
import com.quangtoi.flowerstore.repository.*;
import com.quangtoi.flowerstore.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private CartItemRepository cartItemRepository;
    private FlowerRepository flowerRepository;
    private CartRepository cartRepository;
    private ModelMapper mapper;

    @Override
    @Transactional
    public OrderDto saveOrder(Cart cart, Customer customer) {
        Order order = new Order();
        order.setOrderStatus(Status.PENDING);
        order.setOrderDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        order.setDeliveryDate(calendar.getTime());
        order.setCustomer(customer);
        order.setTotalAmount(cart.getTotalAmount());
        order.setTotalPrice(cart.getTotalPrice());
        order.setCreatedAt(LocalDateTime.now());

        List<OrderDetail> orderDetailList =  new ArrayList<>();
        for (CartItem item : cart.getCartItems()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setFlower(item.getFlower());
            orderDetail.setSubTotal(item.getQuantity()* item.getFlower().getUnitPrice());
            orderDetail.setOrder(order);
            Flower flower = item.getFlower();
            int quantityNeed = item.getQuantity();
            int quantityAvailable = flower.getStockQuantity();
            if (!isValidQuantity(quantityNeed, quantityAvailable))
                return null;
            orderDetail.setQuantity(item.getQuantity());
            flower.setStockQuantity(flower.getStockQuantity() - item.getQuantity());
            flowerRepository.save(flower);
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
            cartItemRepository.delete(item);
        }
        cart.setCartItems(new HashSet<>());
        cart.setTotalAmount(0);
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
        Order orderSaved = orderRepository.save(order);
        return mapper.map(orderSaved, OrderDto.class);
    }

    private boolean isValidQuantity(int qtyNeed, int qtyAvailable){
        if (qtyNeed - qtyAvailable > 0)
            return false;
        return true;
    }

    @Override
    @Transactional
    public OrderDto updateOrder(Long id, Status status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        if (status == Status.CANCELED || status == Status.RETURNED){
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails){
                Flower flower = orderDetail.getFlower();
                int currentQuantityInStock = flower.getStockQuantity();
                flower.setStockQuantity(currentQuantityInStock + orderDetail.getQuantity());
                flowerRepository.save(flower);
            }
        }
        order.setOrderStatus(status);
        Order orderSaved = orderRepository.save(order);
        return mapper.map(orderSaved, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map((o) -> mapper.map(o, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        return mapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrdersByAccount(Account account) {
        Customer customer = account.getCustomer();
        List<Order> orders = orderRepository.findAllByCustomer(customer);
        return orders.stream()
                .map((o) -> mapper.map(o, OrderDto.class))
                .collect(Collectors.toList());
    }
}
