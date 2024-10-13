package com.example.foodorderingsystem.strategy;

import com.example.foodorderingsystem.exception.ItemNotAvailableException;
import com.example.foodorderingsystem.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantSelectionStrategy {
    Optional<Restaurant> selectRestaurant(List<Restaurant> restaurants, String itemName) throws ItemNotAvailableException;
}