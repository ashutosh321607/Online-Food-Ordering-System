package com.example.foodorderingsystem.strategy.enums;

import java.util.Objects;

public enum RestaurantSelectionStrategyType {
    LOWEST_COST,
    HIGHEST_RATING;

    public static RestaurantSelectionStrategyType fromString(final String strategyType) {
        try {
            if (Objects.nonNull(strategyType)) {
                return RestaurantSelectionStrategyType.valueOf(strategyType.toUpperCase());
            }
            return getDefault();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown strategy type: " + strategyType);
        }
    }

    public static RestaurantSelectionStrategyType getDefault() {
        return RestaurantSelectionStrategyType.LOWEST_COST;
    }
}
