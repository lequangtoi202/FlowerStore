package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.FlowerDto;
import com.quangtoi.flowerstore.model.Flower;

import java.util.List;

public interface FlowerService {
    List<FlowerDto> getAllFlowers();
    FlowerDto getFlowerById(Long id);
    List<FlowerDto> getFlowerByCategoryId(Long cateId);
    List<FlowerDto> getFlowerBestSeller();
    List<FlowerDto> getFlowerFavorites();
    List<FlowerDto> getFlowerBySupplierId(Long supplierId);
    List<FlowerDto> getFlowersByKeyword(String kw);
    FlowerDto saveFlower(FlowerDto flowerDto);
    FlowerDto updateFlowerById(FlowerDto flowerDto,Long id);
    void deleteFlowerById(Long id);
}
