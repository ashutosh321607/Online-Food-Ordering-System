package com.example.foodorderingsystem.model;

import com.example.foodorderingsystem.model.enums.RestaurantStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private Integer processingCapacity;
    private Integer currentProcessingCapacity = 0;
    private double rating;

    @Enumerated(EnumType.STRING)
    private RestaurantStatus restaurantStatus = RestaurantStatus.ACTIVE;

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private Set<RestaurantMenuItem> restaurantMenuItems = new HashSet<>();
}
