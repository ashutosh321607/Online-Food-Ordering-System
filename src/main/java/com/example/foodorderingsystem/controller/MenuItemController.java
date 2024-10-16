package com.example.foodorderingsystem.controller;

import com.example.foodorderingsystem.dto.MenuItemDTO;
import com.example.foodorderingsystem.service.MenuItemService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    @NonNull
    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemDTO> createMenuItem(@RequestBody final MenuItemDTO menuItemDTO) {
        final MenuItemDTO createdMenuItem = menuItemService.createMenuItem(menuItemDTO);
        return ResponseEntity.ok(createdMenuItem);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(
            @PathVariable final Long menuItemId,
            @RequestBody final MenuItemDTO menuItemDTO) {
        final MenuItemDTO updatedMenuItem = menuItemService.updateMenuItem(menuItemId, menuItemDTO);
        return ResponseEntity.ok(updatedMenuItem);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        final List<MenuItemDTO> menuItems = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(menuItems);
    }
}
