package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.CartDto;
import com.quangtoi.flowerstore.dto.FlowerDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.model.Previews;
import com.quangtoi.flowerstore.repository.FlowerRepository;
import com.quangtoi.flowerstore.repository.PreviewRepository;
import com.quangtoi.flowerstore.service.AccountService;
import com.quangtoi.flowerstore.service.PreviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalDouble;

@Service
@AllArgsConstructor
public class PreviewServiceImpl implements PreviewService {
    private PreviewRepository previewRepoSitory;
    private FlowerRepository flowerRepository;
    private AccountService accountService;

    @Override
    public FlowerDto countPreviewsById(Long flowerId) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", flowerId));
        int totalPreviews = getTotalPreviews(flowerId);
        double score = avgScore(flowerId);

        FlowerDto flowerDto = new FlowerDto();
        flowerDto.setId(flower.getId());
        flowerDto.setTotalPreviews(totalPreviews);
        flowerDto.setAvgScore(score);
        flowerDto.setCategoryId(flower.getCategory().getId());
        flowerDto.setName(flower.getName());
        flowerDto.setDescription(flower.getDescription());
        flowerDto.setStockQuantity(flower.getStockQuantity());
        flowerDto.setUnitPrice(flower.getUnitPrice());
        flowerDto.setCreatedAt(flower.getCreatedAt());
        flowerDto.setUpdatedAt(flower.getUpdatedAt());
        return flowerDto;
    }

    @Override
    public FlowerDto postPreviewScoreByFlowerId(Long flowerId, double score) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", flowerId));
        List<Previews> previews = previewRepoSitory.findAllByFlower(flower);
        double newAvgScore = (previewRepoSitory.getAvgScoreByFlowerId(flowerId) * previews.size() + score) / (getTotalPreviews(flowerId) + 1);

        // check account is login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Account account = accountService.getAccountByUsername(username);
                if (account != null) {
                    Previews previewsSaved = new Previews();
                    previewsSaved.setPreview(score);
                    previewsSaved.setFlower(flower);
                    previewsSaved.setDatePreview(LocalDateTime.now());
                    previewsSaved.setCreatedAt(LocalDateTime.now());
                    previewsSaved.setAccount(account);
                    previewsSaved.setUpdatedAt(LocalDateTime.now());

                    previewRepoSitory.save(previewsSaved);
                }
            }
        }

        FlowerDto flowerDto = new FlowerDto();
        flowerDto.setId(flower.getId());
        flowerDto.setAvgScore(newAvgScore);
        flowerDto.setTotalPreviews(previews.size() + 1);
        flowerDto.setName(flower.getName());
        flowerDto.setCategoryId(flower.getCategory().getId());
        flowerDto.setDescription(flower.getDescription());
        flowerDto.setStockQuantity(flower.getStockQuantity());
        flowerDto.setUnitPrice(flower.getUnitPrice());
        flowerDto.setCreatedAt(flower.getCreatedAt());
        flowerDto.setUpdatedAt(flower.getUpdatedAt());
        return flowerDto;
    }

    @Override
    public double avgScore(Long flowerId) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", flowerId));
        List<Previews> previews = previewRepoSitory.findAllByFlower(flower);
        OptionalDouble averageScore = previews.stream()
                .mapToDouble(Previews::getPreview)
                .average();
        if (averageScore.isPresent()) {
            return averageScore.getAsDouble();
        } else {
            return -1;
        }
    }


    @Override
    public int getTotalPreviews(Long flowerId) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Flower", "id", flowerId));
        List<Previews> previews = previewRepoSitory.findAllByFlower(flower);
        return previews.size();
    }
}
