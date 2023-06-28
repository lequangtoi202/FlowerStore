package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.StatisticResponse;
import com.quangtoi.flowerstore.repository.StatisticRepository;
import com.quangtoi.flowerstore.service.StatisticService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticRepository statisticRepository;
    @Override
    public StatisticResponse avenueByMonthAndYear(int month, int year) {
        return statisticRepository.avenueByMonthAndYear(month, year);
    }

    @Override
    public StatisticResponse avenueByFlowerId(Long flowerId) {
        return statisticRepository.avenueByFlowerId(flowerId);
    }
}
