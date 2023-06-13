package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.FlowerDto;

public interface PreviewService {
    FlowerDto countPreviewsById(Long flowerId);
    FlowerDto postPreviewScoreByFlowerId(Long flowerId, double score);
    double avgScore(Long flowerId);
    int getTotalPreviews(Long flowerId);
}
