package com.example.cafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Dish domain model
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private Boolean isAvailable;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Checks whether string is a valid Dish Name value
     * @param name string value to be checked
     */
    public static Boolean isValidName(String name) {
        return name != null && !name.isEmpty() && name.length() <= 100;
    }

    /**
     * Checks whether string is a valid Dish Description value
     * @param description string value to be checked
     */
    public static Boolean isValidDescription(String description) {
        return description == null || !description.isEmpty() && description.length() <= 1000;
    }

    /**
     * Checks whether number is a valid Dish Price value
     * @param price number to be checked
     */
    public static Boolean isValidPrice(BigDecimal price) {
        if (price == null) {
            return false;
        }
        String[] priceChunks = price.toString().replace("-", "").split("\\.");
        if (priceChunks.length > 1 && priceChunks[1].length() > 2) {
            return false;
        }
        return priceChunks[0].length() <= 8;
    }

    /**
     * Checks whether number is a valid Dish Quantity value
     * @param quantity number to be checked
     */
    public static Boolean isValidQuantity(Integer quantity) {
        return quantity != null && quantity < 1000000;
    }

    /**
     * Checks whether boolean is a valid Dish Is Available value
     * @param isAvailable boolean to be checked
     */
    public static Boolean isValidIsAvailable(Boolean isAvailable) {
        return isAvailable != null;
    }

    /**
     * Checks if Dish can be ordered based on availability and quantity
     */
    public Boolean canBeOrdered() {
        return isAvailable && quantity > 0;
    }
}
