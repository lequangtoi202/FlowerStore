package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto saveCategory(CategoryDto categoryDto);
    CategoryDto updateCategoryById(CategoryDto categoryDto, Long id);
    void deleteCategoryById(Long id);
    CategoryDto getCategoryById(Long id);
}
