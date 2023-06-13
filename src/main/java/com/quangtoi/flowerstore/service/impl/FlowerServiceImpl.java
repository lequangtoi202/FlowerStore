package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.FlowerDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Category;
import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.model.Supplier;
import com.quangtoi.flowerstore.repository.CategoryRepository;
import com.quangtoi.flowerstore.repository.FlowerRepository;
import com.quangtoi.flowerstore.repository.SupplierRepository;
import com.quangtoi.flowerstore.service.FlowerService;
import com.quangtoi.flowerstore.service.PreviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlowerServiceImpl implements FlowerService {
    private FlowerRepository flowerRepository;
    private CategoryRepository categoryRepository;
    private SupplierRepository supplierRepository;
    private PreviewService previewService;
    private ModelMapper mapper;

    @Override
    public List<FlowerDto> getAllFlowers() {
        return flowerRepository.findAll()
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FlowerDto getFlowerById(Long id) {
        Flower flower = flowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", id));

        return mapper.map(flower, FlowerDto.class);
    }

    @Override
    public List<FlowerDto> getFlowerByCategoryId(Long cateId) {
        Category category = categoryRepository.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", cateId));

        List<Flower> flowers = flowerRepository.findByCategory(category);

        return flowers
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowerDto> getFlowerBestSeller() {
        List<Flower> flowers = flowerRepository.findByBestSeller();
        List<FlowerDto> flowerDtos = new ArrayList<>();
        for (Flower f : flowers){
            FlowerDto flowerDto = new FlowerDto();
            flowerDto.setId(f.getId());
            flowerDto.setName(f.getName());
            flowerDto.setDescription(f.getDescription());
            flowerDto.setCategoryId(f.getCategory().getId());
            flowerDto.setUnitPrice(f.getUnitPrice());
            flowerDto.setCreatedAt(f.getCreatedAt());
            flowerDto.setUpdatedAt(f.getUpdatedAt());
            flowerDto.setTotalPreviews(previewService.getTotalPreviews(f.getId()));
            flowerDto.setAvgScore(previewService.avgScore(f.getId()));

            flowerDtos.add(flowerDto);
        }
        return flowerDtos;
    }

    @Override
    public List<FlowerDto> getFlowerFavorites() {
        return flowerRepository.findByFavorites()
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowerDto> getFlowerBySupplierId(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "supplier id", supplierId));

        List<Flower> flowers = flowerRepository.getAllBySuppliers(supplier.getId());

        return flowers
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowerDto> getFlowersByKeyword(String kw) {
        return flowerRepository.getFlowersByKeyword(kw)
                .stream()
                .map(f -> mapper.map(f, FlowerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FlowerDto saveFlower(FlowerDto flowerDto) {
        Flower flower = new Flower();
        flower.setStockQuantity(0);
        flower.setDescription(flowerDto.getDescription());
        flower.setName(flowerDto.getName());
        flower.setCreatedAt(LocalDateTime.now());
        flower.setUnitPrice(flowerDto.getUnitPrice());
        flower.setUpdatedAt(LocalDateTime.now());
        Category category = categoryRepository
                .findById(flowerDto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "id",
                                flowerDto.getCategoryId()));
        System.out.println(category);
        flower.setCategory(category);
        FlowerDto flowerDtoSaved = mapper.map(flowerRepository.save(flower), FlowerDto.class);
        flowerDto.setStockQuantity(0);
        flowerDto.setAvgScore(0.0);
        flowerDto.setTotalPreviews(0);
        return flowerDtoSaved;
    }

    @Override
    public FlowerDto updateFlowerById(FlowerDto flowerDto, Long id) {
        Flower flowerSaved = flowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", id));
        flowerSaved.setId(flowerDto.getId());
        Category category = categoryRepository.findById(flowerDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", flowerDto.getCategoryId()));

        flowerSaved.setCategory(category);
        flowerSaved.setName(flowerDto.getName());
        flowerSaved.setDescription(flowerDto.getDescription());
        flowerSaved.setStockQuantity(flowerDto.getStockQuantity());
        flowerSaved.setUnitPrice(flowerDto.getUnitPrice());
        flowerSaved.setUpdatedAt(LocalDateTime.now());
        Flower flowerUpdated = flowerRepository.save(flowerSaved);
        FlowerDto flowerDtoResponse = previewService.countPreviewsById(flowerUpdated.getId());
        return flowerDtoResponse;
    }

    @Override
    public void deleteFlowerById(Long id) {
        Flower flowerSaved = flowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", id));
        flowerRepository.delete(flowerSaved);
    }
}
