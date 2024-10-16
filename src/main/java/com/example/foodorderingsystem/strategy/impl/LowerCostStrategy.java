package com.example.foodorderingsystem.strategy.impl;

import com.example.foodorderingsystem.dto.FoodOrderRequestDTO;
import com.example.foodorderingsystem.exception.ItemNotAvailableException;
import com.example.foodorderingsystem.model.Restaurant;
import com.example.foodorderingsystem.model.RestaurantMenuItem;
import com.example.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component("lowestCostSelectionStrategy")
public class LowerCostStrategy extends RestaurantSelectionStrategy {

    @Override
    public Optional<Restaurant> selectRestaurant(final List<Restaurant> restaurantList,
                                                 final FoodOrderRequestDTO orderRequestDTO) {
        final List<Restaurant> eligibleRestaurants = restaurantList.stream()
                .filter(restaurant -> isRestaurantEligible(restaurant, orderRequestDTO))
                .toList();
        if (eligibleRestaurants.isEmpty()) {
            return Optional.empty();
        }

        return eligibleRestaurants.stream()
                .min(Comparator.comparingDouble(restaurant -> calculateTotalCost(restaurant, orderRequestDTO)));

    }

    private double calculateTotalCost(final Restaurant restaurant, final FoodOrderRequestDTO orderRequestDTO) {
        return orderRequestDTO.getItems().entrySet().stream()
                .mapToDouble(entry -> {
                    final Long menuItemId = entry.getKey();
                    final Integer quantity = entry.getValue();
                    final RestaurantMenuItem restaurantMenuItem = restaurant.getRestaurantMenuItems().stream()
                            .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                            .findFirst()
                            .orElseThrow(() -> new ItemNotAvailableException(
                                    "Item " + menuItemId + " not available in restaurant: " + restaurant.getId()));
                    return restaurantMenuItem.getPrice() * quantity;
                }).sum();
    }
}
