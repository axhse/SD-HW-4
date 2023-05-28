package com.example.cafe.api.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Dish creation response body message
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DishCreationMessage extends Message {

    public DishCreationMessage(Long dishId) {
        super("Created");
        this.dishId = dishId;
    }

    private Long dishId;
}
