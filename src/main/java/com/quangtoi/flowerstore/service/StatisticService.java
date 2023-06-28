package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.StatisticResponse;

public interface StatisticService {
    StatisticResponse avenueByMonthAndYear(int month, int year);
    StatisticResponse avenueByFlowerId(Long flowerId);
}
