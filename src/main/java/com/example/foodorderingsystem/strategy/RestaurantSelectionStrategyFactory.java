package com.example.foodorderingsystem.strategy;

import com.example.foodorderingsystem.strategy.enums.RestaurantSelectionStrategyType;
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

    public RestaurantSelectionStrategy getStrategy(RestaurantSelectionStrategyType strategyType) {
        switch (strategyType) {
            case LOWEST_COST:
                return lowestCostStrategy;
            case HIGHEST_RATING:
                return highestRatingStrategy;
            default:
                throw new IllegalArgumentException("Unknown strategy type: " + strategyType);
        }
    }
}
