package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.dto.OrderRequestDTO;
import com.example.foodorderingsystem.exception.ItemNotAvailableException;
import com.example.foodorderingsystem.model.MenuItem;
import com.example.foodorderingsystem.model.Restaurant;
import com.example.foodorderingsystem.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    @NonNull
    private final OrderService orderService;

    // Place an order
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody final OrderRequestDTO orderRequest) {
        try {
            final Map<Restaurant, List<MenuItem>> allocation = orderService.placeOrder(orderRequest);
            return ResponseEntity.ok(allocation);
        } catch (ItemNotAvailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // Dispatch items
    @PostMapping("/dispatch")
    public ResponseEntity<?> dispatchItems(@RequestBody final Map<String, Object> dispatchRequest) {
        try {
            final Long restaurantId = Long.parseLong(dispatchRequest.get("restaurantId").toString());
            final List<Map<String, Object>> items = (List<Map<String, Object>>) dispatchRequest.get("items");

            // Convert items to MenuItem objects
            final List<MenuItem> dispatchedItems = new ArrayList<>();
            for (Map<String, Object> itemMap : items) {
                MenuItem item = new MenuItem();
                item.setId(Long.parseLong(itemMap.get("id").toString()));
                item.setName(itemMap.get("name").toString());
                item.setPrice(Double.parseDouble(itemMap.get("price").toString()));
                dispatchedItems.add(item);
            }

            Restaurant restaurant = new Restaurant();
            restaurant.setId(restaurantId);

            orderService.dispatchItems(restaurant, dispatchedItems);
            return ResponseEntity.ok("Items dispatched successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to dispatch items: " + e.getMessage());
        }
    }
}