package com.example.cafe.api.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Order creation response body message
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderCreationMessage extends Message {

    public OrderCreationMessage(Long orderId) {
        super("Created");
        this.orderId = orderId;
    }

    private Long orderId;
}
