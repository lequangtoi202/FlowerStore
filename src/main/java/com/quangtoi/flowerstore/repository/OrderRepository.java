package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.Customer;
import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(Customer customer);
}
