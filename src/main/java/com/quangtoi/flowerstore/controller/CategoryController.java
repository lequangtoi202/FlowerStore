package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.dto.CategoryDto;
import com.quangtoi.flowerstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
@CrossOrigin
public class CategoryController {
    private CategoryService categoryService;

    //test ok
    @Operation(
            summary = "Get all categories REST API"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    //test ok
    @Operation(
            summary = "Get category by id REST API"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id")Long id){
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }

    //test ok
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Create category"
    )
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.saveCategory(categoryDto), HttpStatus.CREATED);
    }

    //test ok
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "update category by id REST API"
    )
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") Long id){
        return ResponseEntity.ok().body(categoryService.updateCategoryById(categoryDto, id));
    }

    //test ok
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Delete category by id REST API"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCateById(@PathVariable("id")Long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok().body("Delete category successfully!");
    }
}
