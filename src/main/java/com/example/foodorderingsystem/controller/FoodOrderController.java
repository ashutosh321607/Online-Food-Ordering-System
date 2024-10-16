package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.dto.FoodOrderRequestDTO;
import com.example.foodorderingsystem.dto.FoodOrderResponseDTO;
import com.example.foodorderingsystem.service.FoodOrderService;
import com.example.foodorderingsystem.strategy.enums.RestaurantSelectionStrategyType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class FoodOrderController {

    @NonNull
    private final FoodOrderService foodOrderService;

    @PostMapping
    public ResponseEntity<FoodOrderResponseDTO> placeOrder(@RequestBody FoodOrderRequestDTO orderRequestDTO,
                                                           @RequestParam String strategyType) {
        final RestaurantSelectionStrategyType restaurantSelectionStrategyType =
                RestaurantSelectionStrategyType.fromString(strategyType);
        final FoodOrderResponseDTO response = foodOrderService.placeOrder(orderRequestDTO, restaurantSelectionStrategyType);
        return Objects.nonNull(response) ? ResponseEntity.ok(response) : ResponseEntity.status(503).build(); // Service Unavailable
    }
}