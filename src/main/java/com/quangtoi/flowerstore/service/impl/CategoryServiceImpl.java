package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.CategoryDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Category;
import com.quangtoi.flowerstore.repository.CategoryRepository;
import com.quangtoi.flowerstore.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private ModelMapper mapper;
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map((cate) -> mapper.map(cate, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        Category categorySaved = categoryRepository.save(category);
        return mapper.map(categorySaved, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategoryById(CategoryDto categoryDto, Long id) {
        Category categorySaved = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        categorySaved.setName(categoryDto.getName());
        Category categoryUpdated = categoryRepository.save(categorySaved);
        return mapper.map(categoryUpdated, CategoryDto.class);
    }

    @Override
    public void deleteCategoryById(Long id) {

        Category categorySaved = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(categorySaved);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category categorySaved = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return mapper.map(categorySaved, CategoryDto.class);
    }
}
