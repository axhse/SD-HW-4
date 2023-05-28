package com.example.cafe.service;

import com.example.cafe.model.CafeOrder;

/**
 * Specifies order completion service
 */
public interface OrderCompleter {

    /**
     * Completes order
     * @param orderId if of order to be completed
     */
    void doOrder(Long orderId);
}
