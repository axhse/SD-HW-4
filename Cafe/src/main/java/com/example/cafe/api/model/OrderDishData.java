package com.example.cafe.api.model;

import com.example.cafe.model.Dish;
import com.example.cafe.model.OrderDish;
import lombok.Data;

import java.math.BigDecimal;

/**
 * OrderDish response body data
 */
@Data
public class OrderDishData {

    public OrderDishData(OrderDish orderDish, Dish dish) {
        id = orderDish.getDishId();
        price = orderDish.getPrice();
        quantity = orderDish.getQuantity();
        if (dish != null) {
            name = dish.getName();
            description = dish.getDescription();
        }
    }

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;
}
