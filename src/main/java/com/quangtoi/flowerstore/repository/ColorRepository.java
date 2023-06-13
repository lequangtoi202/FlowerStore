package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.CartItem;
import com.quangtoi.flowerstore.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}
