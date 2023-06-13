package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.Order;
import com.quangtoi.flowerstore.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrder(Order order);
}
