package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.dto.RestaurantDTO;
import com.example.foodorderingsystem.dto.RestaurantMenuItemDTO;
import com.example.foodorderingsystem.service.RestaurantService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    @NonNull
    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable final Long id) {
        final RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(id);
        return restaurantDTO != null ? ResponseEntity.ok(restaurantDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public RestaurantDTO registerRestaurant(@RequestBody final RestaurantDTO restaurantDTO) {
        return restaurantService.saveOrUpdateRestaurant(restaurantDTO);
    }

    @PostMapping("/{restaurantId}/menu-items")
    public ResponseEntity<Void> addMenuItemsToRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody List<RestaurantMenuItemDTO> menuItemDTOs) {
        restaurantService.addMenuItemsToRestaurant(restaurantId, menuItemDTOs);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unRegisterRestaurant(@PathVariable final Long id) {
        restaurantService.deActiveRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}