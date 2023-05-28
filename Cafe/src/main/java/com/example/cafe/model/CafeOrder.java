package com.example.cafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Order domain model
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CafeOrder {
    public static final Integer MAX_ORDER_SIZE = 100;
    public static final String STATUS_ACCEPTED = "accepted";
    public static final String STATUS_CREATED = "created";
    public static final String STATUS_DONE = "done";
    public static final String STATUS_CANCELED = "canceled";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String status;

    private String specialRequests;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Checks whether string is a valid Special Requests value
     * @param specialRequests string value to be checked
     */
    public static Boolean isValidSpecialRequests(String specialRequests) {
        return specialRequests == null || !specialRequests.isEmpty() && specialRequests.length() <= 1000;
    }
}
