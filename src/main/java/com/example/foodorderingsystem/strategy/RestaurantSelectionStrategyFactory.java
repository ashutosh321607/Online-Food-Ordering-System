package com.example.foodorderingsystem.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RestaurantSelectionStrategyFactory {

    private final RestaurantSelectionStrategy lowestCostStrategy;
    private final RestaurantSelectionStrategy highestRatingStrategy;

    @Autowired
    public RestaurantSelectionStrategyFactory(
            @Qualifier("lowestCostSelectionStrategy") RestaurantSelectionStrategy lowestCostStrategy,
            @Qualifier("highestRatingSelectionStrategy") RestaurantSelectionStrategy highestRatingStrategy) {
        this.lowestCostStrategy = lowestCostStrategy;
        this.highestRatingStrategy = highestRatingStrategy;
    }

    public RestaurantSelectionStrategy getStrategy(final String strategyType) {
        switch (strategyType.toLowerCase()) {
            case "lowest_cost":
                return lowestCostStrategy;
            case "highest_rating":
                return highestRatingStrategy;
            default:
                throw new IllegalArgumentException("Unknown strategy type: " + strategyType);
        }
    }
}
