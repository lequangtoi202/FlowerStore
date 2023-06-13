package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.dto.OrderDetailDto;
import com.quangtoi.flowerstore.dto.OrderDto;
import com.quangtoi.flowerstore.model.*;
import com.quangtoi.flowerstore.service.AccountService;
import com.quangtoi.flowerstore.service.OrderDetailService;
import com.quangtoi.flowerstore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
@CrossOrigin
public class OrderController {

    private OrderService orderService;
    private AccountService accountService;
    private OrderDetailService orderDetailService;

    //test ok
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    //test ok
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id")Long id){
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }


    //test ok
    @GetMapping("/{id}/order-details")
    public ResponseEntity<List<OrderDetailDto>> getOrdersDetailsByOrderId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(orderDetailService.getOrderDetailByOrderId(id));
    }


    //test ok
    @PostMapping
    public ResponseEntity<OrderDto> saveOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Account account = accountService.getAccountByUsername(username);
                Customer customer = account.getCustomer();
                if (customer == null){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                if (account != null) {
                    Cart cart = account.getCart();
                    OrderDto orderDto = orderService.saveOrder(cart, customer);
                    if (orderDto == null)
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    return ResponseEntity.ok().body(orderDto);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //test ok
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable("id")Long id, @RequestParam(value = "status") String statusText){
        String textName = statusText.toUpperCase();
        Status status = Status.valueOf(textName);
        if (status == null){
            return ResponseEntity.badRequest().body(null);
        }
        OrderDto orderDto = orderService.updateOrder(id, status);
        return ResponseEntity.ok().body(orderDto);
    }

}
