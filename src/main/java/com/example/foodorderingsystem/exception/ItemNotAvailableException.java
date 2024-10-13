package com.example.foodorderingsystem.exception;

public class ItemNotAvailableException extends Exception {
    public ItemNotAvailableException(String message) {
        super(message);
    }
}