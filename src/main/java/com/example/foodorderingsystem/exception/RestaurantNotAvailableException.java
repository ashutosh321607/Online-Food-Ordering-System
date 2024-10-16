package com.example.foodorderingsystem.exception;

public class RestaurantNotAvailableException extends RuntimeException {
    public RestaurantNotAvailableException(String message) {
        super(message);
    }
}
