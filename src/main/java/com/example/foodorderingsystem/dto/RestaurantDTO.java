package com.example.foodorderingsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;

@Data
@Builder
public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    private Integer ProcessingCapacityCount;
    private Integer CurrentProcessingCount;
    private Integer Rating;
}
