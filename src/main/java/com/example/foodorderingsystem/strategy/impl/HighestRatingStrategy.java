package com.example.foodorderingsystem.strategy.impl;

import com.example.foodorderingsystem.dto.FoodOrderRequestDTO;
import com.example.foodorderingsystem.model.Restaurant;
import com.example.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component("highestRatingSelectionStrategy")
public class HighestRatingStrategy extends RestaurantSelectionStrategy {

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
                .max(Comparator.comparing(Restaurant::getRating));
    }
}
