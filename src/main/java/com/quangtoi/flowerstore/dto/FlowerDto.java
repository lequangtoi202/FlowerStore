package com.quangtoi.flowerstore.dto;

import com.quangtoi.flowerstore.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowerDto {
    private Long id;
    private String name;
    private String description;
    private double unitPrice;
    private String urlImage;
    private int stockQuantity;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int totalPreviews;
    private double avgScore;
}
