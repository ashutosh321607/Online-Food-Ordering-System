package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.dto.RestaurantDTO;
import com.example.foodorderingsystem.service.RestaurantService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return restaurantService.createRestaurant(restaurantDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable final Long id,
                                                          @RequestBody final RestaurantDTO restaurantDTO) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unRegisterRestaurant(@PathVariable final Long id) {
        restaurantService.deActiveRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}