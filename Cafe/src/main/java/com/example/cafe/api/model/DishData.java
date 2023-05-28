package com.example.cafe.api.model;

import com.example.cafe.model.Dish;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Dish response body data
 */
@Data
public class DishData {

    public DishData(Dish dish) {
        id = dish.getId();
        name = dish.getName();
        description = dish.getDescription();
        price = dish.getPrice();
        quantity = dish.getQuantity();
        isAvailable = dish.getIsAvailable();
    }

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private Boolean isAvailable;
}
