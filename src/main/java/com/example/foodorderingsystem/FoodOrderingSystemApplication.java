package com.example.foodorderingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FoodOrderingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodOrderingSystemApplication.class, args);
    }
}
