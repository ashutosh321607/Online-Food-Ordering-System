package com.example.foodorderingsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class OrderRequestDTO {
    private HashMap<Long, Integer> items; // key: itemId, value: quantity
}
