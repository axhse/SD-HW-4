package com.example.cafe.api.params;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Dish request params
 */
@Data
public class DishParams {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private Boolean isAvailable;
}
