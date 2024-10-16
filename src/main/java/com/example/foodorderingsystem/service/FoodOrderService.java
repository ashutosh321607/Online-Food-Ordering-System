package com.example.foodorderingsystem.service;

import com.example.foodorderingsystem.dto.FoodOrderRequestDTO;
import com.example.foodorderingsystem.dto.FoodOrderResponseDTO;
import com.example.foodorderingsystem.exception.RestaurantNotAvailableException;
import com.example.foodorderingsystem.model.FoodOrder;
import com.example.foodorderingsystem.model.Restaurant;
import com.example.foodorderingsystem.model.enums.OrderStatus;
import com.example.foodorderingsystem.repository.FoodOrderRepository;
import com.example.foodorderingsystem.repository.RestaurantRepository;
import com.example.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import com.example.foodorderingsystem.strategy.RestaurantSelectionStrategyFactory;
import com.example.foodorderingsystem.strategy.enums.RestaurantSelectionStrategyType;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodOrderService {

    @NonNull
    private final RestaurantRepository restaurantRepository;
    @NonNull
    private final FoodOrderRepository orderRepository;
    @NonNull
    private final RestaurantSelectionStrategyFactory restaurantSelectionStrategyFactory;

    @Transactional
    public FoodOrderResponseDTO placeOrder(final FoodOrderRequestDTO orderRequestDTO,
                                           final RestaurantSelectionStrategyType restaurantSelectionStrategyType) {
        final List<Restaurant> availableRestaurants = restaurantRepository.findAll();
        final RestaurantSelectionStrategy strategy = restaurantSelectionStrategyFactory
                .getStrategy(restaurantSelectionStrategyType);
        final Optional<Restaurant> selectedRestaurantOpt = strategy.selectRestaurant(availableRestaurants, orderRequestDTO);
        if (selectedRestaurantOpt.isEmpty()) {
            throw new RestaurantNotAvailableException("No restaurant is available to fulfill the order.");
        }
        final int totalItemsOrdered = orderRequestDTO.getItems().values().stream().mapToInt(Integer::intValue).sum();
        final Restaurant selectedRestaurant = selectedRestaurantOpt.get();

        selectedRestaurant
                .setCurrentProcessingCapacity(selectedRestaurant.getCurrentProcessingCapacity() + totalItemsOrdered);

        final FoodOrder foodOrder = FoodOrder.builder()
                .restaurant(selectedRestaurant)
                .items(orderRequestDTO.getItems())
                .status(OrderStatus.IN_PROGRESS)
                .build();
        orderRepository.save(foodOrder);
        final Double totalCost = selectedRestaurant.getRestaurantMenuItems().stream()
                .filter(restaurantMenuItem -> orderRequestDTO.getItems()
                        .containsKey(restaurantMenuItem.getMenuItem().getId()))
                .mapToDouble(restaurantMenuItem -> restaurantMenuItem.getPrice() * orderRequestDTO.getItems()
                        .get(restaurantMenuItem.getMenuItem().getId()))
                .sum();
        return FoodOrderResponseDTO.builder()
                .restaurantId(selectedRestaurant.getId())
                .orderId(foodOrder.getId())
                .items(orderRequestDTO.getItems())
                .status(foodOrder.getStatus().name())
                .totalCost(totalCost)
                .build();
    }
}
