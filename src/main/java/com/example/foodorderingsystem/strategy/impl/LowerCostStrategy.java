package com.example.foodorderingsystem.strategy.impl;

import com.example.foodorderingsystem.exception.ItemNotAvailableException;
import com.example.foodorderingsystem.model.Restaurant;
import com.example.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("lowestCostSelectionStrategy")
public class LowerCostStrategy implements RestaurantSelectionStrategy {

    @Override
    public Optional<Restaurant> selectRestaurant(List<Restaurant> restaurants, String itemName) throws ItemNotAvailableException {
        return Optional.empty();
//        return restaurants.stream()
//                .filter(restaurant -> restaurant.getMenuItems().stream().anyMatch(menuItem -> menuItem.getName().equalsIgnoreCase(itemName)))
//                .min(Comparator.comparingDouble(restaurant ->
//                        restaurant.getMenuItems().stream()
//                                .filter(menuItem -> menuItem.getName().equalsIgnoreCase(itemName))
//                                .mapToDouble(MenuItem::getPrice)
//                                .min()
//                                .orElse(Double.MAX_VALUE)
//                ));
    }
}
