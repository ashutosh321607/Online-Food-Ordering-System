package com.example.foodorderingsystem.repository;

import com.example.foodorderingsystem.model.RestaurantMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {
}