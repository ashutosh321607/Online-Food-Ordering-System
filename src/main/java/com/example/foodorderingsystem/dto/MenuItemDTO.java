package com.example.foodorderingsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuItemDTO {
    private Long id;
    private String name;
    private double price;
}