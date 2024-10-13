# Online Food Ordering System

## Overview

The **Online Food Ordering System** is designed to streamline the process of food ordering from various restaurants, providing customers with an easy way to browse menus, select multiple items, and place orders efficiently. The system also ensures that restaurants' capacities are managed dynamically, with configurable strategies for selecting restaurants based on item price or restaurant rating.

## Functional Requirements

1. **Restaurant Management:**
    - Restaurants can register themselves on the system.
    - Restaurants can update their menus, including food items and prices.
    - Restaurants have a maximum processing capacity, beyond which they cannot accept orders until previous items are dispatched.

2. **Customer Interaction:**
    - Customers can view all available items from all registered restaurants.
    - Customers can select multiple items and quantities for their order.
    - The system ensures that all items in the order are deliverable by at least one restaurant.
    - If multiple restaurants provide the same item, a configurable **restaurant selection strategy** is used to choose the best restaurant to fulfill the item based on specific criteria (e.g., lowest cost, highest rating).

3. **Order Management:**
    - The system automatically divides the customer's order across multiple restaurants if needed.
    - Each restaurant informs the system when an item is dispatched, releasing the restaurant's processing capacity for further orders.

4. **Restaurant Selection Strategy:**
    - The system supports configurable strategies to determine which restaurant should fulfill an order.
    - One example is the **lower cost strategy**, which selects the restaurant offering an item at the lowest price.
    - Another strategy might prioritize restaurants with higher ratings.

5. **Concurrency and Scalability:**
    - The system handles concurrent orders efficiently, ensuring that restaurant capacities are not breached.
    - The solution is designed to be scalable and resilient in a real-world environment.

## Non-Functional Requirements

- **Configurability**: The restaurant selection strategy must be easily configurable to allow for different business rules.
- **Concurrency**: The system must handle concurrent orders without breaching restaurant capacity constraints.
- **Data Integrity**: Ensure consistency of order states, menu updates, and restaurant capacity across the system.
- **Performance**: The system should be able to handle high volumes of traffic without significant performance degradation.
- **Scalability**: The system should be designed to accommodate the addition of new restaurants and menu items without impacting existing functionality.

## Technical Architecture

The system is built using **Spring Boot** and follows a **REST API** architecture for interactions between customers, restaurants, and the system backend.

### Key Components:
- **Controller Layer**: Exposes RESTful APIs for customer and restaurant interactions.
- **Service Layer**: Handles business logic for managing orders, restaurant capacities, and the restaurant selection strategies.
- **Repository Layer**: Manages the database interactions using Spring Data JPA, including restaurant information, menus, and orders.
- **Database**: The system uses an in-memory **H2 Database** for development, which can be replaced with a production-grade database (e.g., MySQL or PostgreSQL) as required.
- **Concurrency Control**: Concurrency issues are handled using locks and transaction management in Spring to ensure consistency when multiple customers place orders simultaneously.

### API Endpoints:

1. **Restaurant Management**
    - `POST /restaurants`: Register a new restaurant.
    - `PUT /restaurants/{id}/menu`: Update the restaurant’s menu.

2. **Customer Ordering**
    - `GET /items`: Retrieve a list of all available items from all restaurants.
    - `POST /orders`: Place an order, specifying items and quantities.

3. **Order Management**
    - `POST /restaurants/{id}/dispatch`: Mark an item as dispatched, freeing up restaurant capacity.

### Example Flow:

1. **Restaurant Registration:**
    - Restaurants register by providing their details and menu items. Each restaurant has a defined processing capacity.

2. **Customer Order:**
    - A customer requests a list of available items and selects multiple items to order.
    - The system identifies restaurants that can fulfill the order, using the **restaurant selection strategy** to choose the best restaurant for each item.

3. **Order Fulfillment:**
    - The selected restaurants begin preparing the order.
    - Once items are dispatched, the restaurant updates the system, and their processing capacity is released.

## Example Scenarios

1. **Scenario 1: Single Restaurant, Single Item**
    - A customer orders a pizza from a single restaurant, and the restaurant processes the order within its capacity.

2. **Scenario 2: Multiple Restaurants, Multiple Items**
    - A customer orders sushi, pizza, and a burger. The system selects three different restaurants based on availability and cost, ensuring that each item can be fulfilled without breaching restaurant capacities.

3. **Scenario 3: Full Capacity**
    - A restaurant reaches its full processing capacity and temporarily stops accepting new orders until current orders are dispatched.

## Conclusion

The **Online Food Ordering System** efficiently manages customer orders across multiple restaurants, ensuring smooth order fulfillment and optimal use of restaurant capacity. The configurable restaurant selection strategy provides flexibility for choosing restaurants based on various factors, making the system adaptable to different business requirements.
