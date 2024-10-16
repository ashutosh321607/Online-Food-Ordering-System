package com.example.foodorderingsystem.service;

import com.example.foodorderingsystem.dto.MenuItemDTO;
import com.example.foodorderingsystem.exception.ItemNotAvailableException;
import com.example.foodorderingsystem.model.MenuItem;
import com.example.foodorderingsystem.repository.MenuItemRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    @NonNull
    private final MenuItemRepository menuItemRepository;
    @NonNull
    private final ModelMapper modelMapper;

    @Transactional
    public MenuItemDTO createMenuItem(@NonNull final MenuItemDTO menuItemDTO) {
        MenuItem menuItem = modelMapper.map(menuItemDTO, MenuItem.class);
        menuItem = menuItemRepository.save(menuItem);
        return modelMapper.map(menuItem, MenuItemDTO.class);
    }

    @Transactional
    public MenuItemDTO updateMenuItem(@NonNull final Long menuItemId,
                                      @NonNull final MenuItemDTO menuItemDTO) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ItemNotAvailableException("MenuItem not found"));
        menuItem.setName(menuItemDTO.getName());

        menuItem = menuItemRepository.save(menuItem);
        return modelMapper.map(menuItem, MenuItemDTO.class);
    }

    public List<MenuItemDTO> getAllMenuItems() {
        final List<MenuItem> menuItems = menuItemRepository.findAll();
        return menuItems.stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDTO.class))
                .collect(Collectors.toList());
    }
}
