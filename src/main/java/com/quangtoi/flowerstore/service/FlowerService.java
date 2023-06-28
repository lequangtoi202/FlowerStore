package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.FlowerDto;
import com.quangtoi.flowerstore.model.Flower;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FlowerService {
    List<FlowerDto> getAllFlowers();
    FlowerDto getFlowerById(Long id);
    List<FlowerDto> getFlowerByCategoryId(Long cateId);
    List<FlowerDto> getFlowerBestSeller();
    List<FlowerDto> getFlowerFavorites();
    List<FlowerDto> getFlowerBySupplierId(Long supplierId);
    List<FlowerDto> getFlowersByKeyword(String kw);
    FlowerDto saveFlower(FlowerDto flowerDto, MultipartFile imageFlower);
    FlowerDto updateFlowerById(FlowerDto flowerDto,Long id, MultipartFile imageFlower);
    void deleteFlowerById(Long id);
    Integer getAmountOfSoldFlowers(Long flowerId);
}
