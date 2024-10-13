package com.example.foodorderingsystem.service;

import com.example.foodorderingsystem.dto.OrderRequestDTO;
import com.example.foodorderingsystem.exception.ItemNotAvailableException;
import com.example.foodorderingsystem.model.MenuItem;
import com.example.foodorderingsystem.model.Restaurant;
import com.example.foodorderingsystem.repository.RestaurantRepository;
import com.example.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import com.example.foodorderingsystem.strategy.RestaurantSelectionStrategyFactory;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @NonNull
    private final RestaurantRepository restaurantRepository;
    @NonNull
    private final RestaurantSelectionStrategy selectionStrategy;
    @NonNull
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(@NonNull RestaurantRepository restaurantRepository,
                        @NonNull RestaurantSelectionStrategyFactory strategyFactory,
                        @NonNull ModelMapper modelMapper) {
        this.selectionStrategy = strategyFactory.getStrategy("lowest_cost");
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }

    // Place an order
    public Map<Restaurant, List<MenuItem>> placeOrder(OrderRequestDTO orderRequest) throws ItemNotAvailableException {
        Map<Restaurant, List<MenuItem>> orderAllocation = new HashMap<>();

        for (Map.Entry<String, Integer> entry : orderRequest.getItems().entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();

            for (int i = 0; i < quantity; i++) {
                // Find the best restaurant for this item
                Optional<Restaurant> restaurantOpt = selectionStrategy.selectRestaurant(restaurantRepository.findAll(), itemName);
                if (restaurantOpt.isEmpty()) {
                    throw new ItemNotAvailableException("Item " + itemName + " is not available in any restaurant.");
                }

                Restaurant restaurant = restaurantOpt.get();

                // Check capacity
                if (restaurant.getMenuItems().stream().anyMatch(mi -> mi.getName().equals(itemName))) {
                    if (restaurant.getProcessingCapacity() > 0) {
                        restaurant.setProcessingCapacity(restaurant.getProcessingCapacity() - 1);
                        restaurantRepository.save(restaurant);
                    } else {
                        throw new RuntimeException("Restaurant " + restaurant.getName() + " has reached its processing capacity.");
                    }

                    // Allocate the item
                    MenuItem menuItem = restaurant.getMenuItems().stream()
                            .filter(mi -> mi.getName().equals(itemName))
                            .findFirst()
                            .orElse(null);
                    if (menuItem != null) {
                        orderAllocation.computeIfAbsent(restaurant, k -> new ArrayList<>()).add(menuItem);
                    }
                }
            }
        }

        return orderAllocation;
    }

    // Dispatch items (release capacity)
    public void dispatchItems(Restaurant restaurant, List<MenuItem> dispatchedItems) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurant.getId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurant.getId()));

        existingRestaurant.setProcessingCapacity(existingRestaurant.getProcessingCapacity() + dispatchedItems.size());
        restaurantRepository.save(existingRestaurant);
    }
}
