package com.example.foodorderingsystem.service;

import com.example.foodorderingsystem.dto.RestaurantDTO;
import com.example.foodorderingsystem.dto.RestaurantMenuItemDTO;
import com.example.foodorderingsystem.exception.InvalidIdException;
import com.example.foodorderingsystem.exception.ItemNotAvailableException;
import com.example.foodorderingsystem.model.MenuItem;
import com.example.foodorderingsystem.model.Restaurant;
import com.example.foodorderingsystem.model.RestaurantMenuItem;
import com.example.foodorderingsystem.model.enums.RestaurantStatus;
import com.example.foodorderingsystem.repository.MenuItemRepository;
import com.example.foodorderingsystem.repository.RestaurantMenuItemRepository;
import com.example.foodorderingsystem.repository.RestaurantRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    @NonNull
    private final ModelMapper modelMapper;

    @Transactional
    public List<RestaurantDTO> getAllRestaurants() {
        final List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public RestaurantDTO getRestaurantById(@NonNull final Long restaurantId) {
        final Restaurant restaurant = fetchRestaurantById(restaurantId);
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    @Transactional
    public RestaurantDTO saveOrUpdateRestaurant(@NonNull final RestaurantDTO restaurantDTO) {
        final Restaurant restaurant = modelMapper.map(restaurantDTO, Restaurant.class);
        final Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return modelMapper.map(savedRestaurant, RestaurantDTO.class);
    }

    @Transactional
    public void deActiveRestaurant(@NonNull final Long restaurantId) {
        final Restaurant restaurant = fetchRestaurantById(restaurantId);

        restaurant.setRestaurantStatus(RestaurantStatus.INACTIVE);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void addMenuItemsToRestaurant(@NonNull final Long restaurantId,
                                         @NonNull final List<RestaurantMenuItemDTO> menuItemDTOs) {
        final Restaurant restaurant = fetchRestaurantById(restaurantId);
        final Set<RestaurantMenuItem> restaurantMenuItems = menuItemDTOs.stream()
                .map(dto -> createRestaurantMenuItem(restaurant, dto))
                .collect(Collectors.toSet());

        restaurantMenuItemRepository.saveAll(restaurantMenuItems);
    }

    private Restaurant fetchRestaurantById(final Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new InvalidIdException("Restaurant not found"));
    }

    private RestaurantMenuItem createRestaurantMenuItem(@NonNull final Restaurant restaurant,
                                                        @NonNull final RestaurantMenuItemDTO dto) {
        final MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new ItemNotAvailableException("MenuItem not found"));

        return RestaurantMenuItem.builder()
                .menuItem(menuItem)
                .restaurant(restaurant)
                .price(dto.getPrice())
                .build();
    }
}
