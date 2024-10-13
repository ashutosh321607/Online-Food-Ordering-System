package com.example.foodorderingsystem.service;

import com.example.foodorderingsystem.dto.RestaurantDTO;
import com.example.foodorderingsystem.exception.InvalidIdException;
import com.example.foodorderingsystem.model.MenuItem;
import com.example.foodorderingsystem.model.Restaurant;
import com.example.foodorderingsystem.model.enums.RestaurantStatus;
import com.example.foodorderingsystem.repository.MenuItemRepository;
import com.example.foodorderingsystem.repository.RestaurantRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    @NonNull
    private final RestaurantRepository restaurantRepository;
    @NonNull
    private final MenuItemRepository menuItemRepository;
    @NonNull
    private final ModelMapper modelMapper;

    public List<RestaurantDTO> getAllRestaurants() {
        final List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class))
                .collect(Collectors.toList());
    }

    public RestaurantDTO getRestaurantById(final Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        return restaurant.map(r -> modelMapper.map(r, RestaurantDTO.class)).orElse(null);
    }

    @Transactional
    public RestaurantDTO createRestaurant(final RestaurantDTO restaurantDTO) {
        Restaurant restaurant = modelMapper.map(restaurantDTO, Restaurant.class);

        // Fetch and set menu items from the DB
        Set<MenuItem> menuItems = restaurantDTO.getMenuItems()
                .stream()
                .map(itemDto -> menuItemRepository.findById(itemDto.getId())
                        .orElseThrow(() -> new RuntimeException("MenuItem not found")))
                .collect(Collectors.toSet());
        restaurant.setMenuItems(menuItems);

        restaurantRepository.save(restaurant);
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    @Transactional
    public RestaurantDTO updateRestaurant(@NonNull final Long restaurantId,
                                          @NonNull final RestaurantDTO restaurantDTO) {
        Restaurant restaurant = fetchRestaurantById(restaurantId);

        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());

        // Update menu items if needed
        Set<MenuItem> menuItems = restaurantDTO.getMenuItems()
                .stream()
                .map(itemDto -> menuItemRepository.findById(itemDto.getId())
                        .orElseThrow(() -> new RuntimeException("MenuItem not found")))
                .collect(Collectors.toSet());
        restaurant.setMenuItems(menuItems);

        restaurantRepository.save(restaurant);
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    @Transactional
    public void deActiveRestaurant(@NonNull final Long restaurantId) {
        final Restaurant restaurant = fetchRestaurantById(restaurantId);

        restaurant.setRestaurantStatus(RestaurantStatus.INACTIVE);
        restaurantRepository.save(restaurant);
    }

    private Restaurant fetchRestaurantById(final Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new InvalidIdException("Restaurant not found"));
    }
}
