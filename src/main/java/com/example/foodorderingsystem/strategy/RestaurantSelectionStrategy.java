package com.example.foodorderingsystem.strategy;

import com.example.foodorderingsystem.dto.FoodOrderRequestDTO;
import com.example.foodorderingsystem.model.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class RestaurantSelectionStrategy {

    public abstract Optional<Restaurant> selectRestaurant(
            final List<Restaurant> restaurantList,
            final FoodOrderRequestDTO orderRequestDTO);

    public boolean isRestaurantEligible(final Restaurant restaurant,
                                        final FoodOrderRequestDTO orderRequestDTO) {
        final Set<Long> restaurantMenuItemIds = restaurant.getRestaurantMenuItems().stream()
                .map(menuItem -> menuItem.getMenuItem().getId())
                .collect(Collectors.toSet());
        if (!restaurantMenuItemIds.containsAll(orderRequestDTO.getItems().keySet())) {
            return false;
        }
        final int totalItemsToOrder = orderRequestDTO.getItems().values().stream().mapToInt(Integer::intValue).sum();
        final int availableCapacity = restaurant.getProcessingCapacity() - restaurant.getCurrentProcessingCapacity();

        return totalItemsToOrder <= availableCapacity;
    }
}