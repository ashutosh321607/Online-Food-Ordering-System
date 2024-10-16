package com.example.foodorderingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderRequestDTO {
    private HashMap<Long, Integer> items; // key: itemId, value: quantity
}
