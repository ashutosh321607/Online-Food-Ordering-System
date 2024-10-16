package com.example.foodorderingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderResponseDTO {
    private Long orderId;
    private Long restaurantId;
    private Map<Long, Integer> items;
    private String status;
    private Double totalCost;
}
