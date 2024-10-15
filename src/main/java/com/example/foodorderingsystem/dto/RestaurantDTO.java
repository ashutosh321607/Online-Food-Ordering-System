package com.example.foodorderingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private String name;
    private String address;
    private Integer processingCapacity;
    private Integer rating;
}
