package com.example.foodorderingsystem.strategy.impl;

import com.example.foodorderingsystem.model.Restaurant;
import com.example.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("highestRatingSelectionStrategy")
public class HighestRatingStrategy implements RestaurantSelectionStrategy {

    @Override
    public Optional<Restaurant> selectRestaurant(final List<Restaurant> restaurants,
                                                 final String itemName) {
        return Optional.empty();
//        return restaurants.stream()
//                .filter(restaurant -> restaurant.getMenuItems().stream()
//                        .anyMatch(menuItem -> menuItem.getName().equalsIgnoreCase(itemName)))
//                .max(Comparator.comparingDouble(Restaurant::getRating));
    }
}
