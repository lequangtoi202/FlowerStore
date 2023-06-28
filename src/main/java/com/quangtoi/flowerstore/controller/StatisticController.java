package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.dto.StatisticResponse;
import com.quangtoi.flowerstore.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/statistics")
@CrossOrigin
public class StatisticController {
    private StatisticService statisticService;

    @Operation(summary = "Get statistic by month and year REST API")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<StatisticResponse> getStatisticByMonthAndYear(@RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year){
        int monthValue = 0;
        int yearValue = 0;
        if (month == null)
            monthValue = LocalDate.now().getMonthValue();
        else if (year == null)
            yearValue = LocalDate.now().getYear();
        else{
            monthValue = month;
            yearValue = year;
        }
        StatisticResponse statisticResponse = statisticService.avenueByMonthAndYear(monthValue, yearValue);
        return ResponseEntity.ok().body(statisticResponse);
    }

    @Operation(summary = "Get statistic by flower id REST API")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<StatisticResponse> getStatisticByFlowerId(@PathVariable("id") Long flowerId){
        StatisticResponse statisticResponse = statisticService.avenueByFlowerId(flowerId);
        return ResponseEntity.ok().body(statisticResponse);
    }
}
