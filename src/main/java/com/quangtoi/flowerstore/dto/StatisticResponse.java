package com.quangtoi.flowerstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StatisticResponse {
    private int totalQuantity;
    private double avenue;

    public StatisticResponse(int totalQuantity, double avenue){
        this.totalQuantity = totalQuantity;
        this.avenue = avenue;
    }
}
