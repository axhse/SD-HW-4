package com.example.cafe.service;

import com.example.cafe.model.CafeOrder;
import com.example.cafe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implements order completion service
 */
@Service
@RequiredArgsConstructor
public class OrderCompleterImpl implements OrderCompleter {
    public static final Integer UNIT_PAUSE_IN_SECONDS = 10;
    private final OrderRepository orderRepository;

    public void doOrder(Long orderId) {
        sleep(1);
        var optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty() || optionalOrder.get().getStatus().equals(CafeOrder.STATUS_CANCELED)) {
            return;
        }
        var currentOrder = optionalOrder.get();
        currentOrder.setStatus(CafeOrder.STATUS_ACCEPTED);
        orderRepository.save(currentOrder);
        sleep(2);
        optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty() || optionalOrder.get().getStatus().equals(CafeOrder.STATUS_CANCELED)) {
            return;
        }
        currentOrder = optionalOrder.get();
        currentOrder.setStatus(CafeOrder.STATUS_DONE);
        orderRepository.save(currentOrder);
    }

    private void sleep(Integer pauseUnitCount) {
        try {
            Thread.sleep(1000L * UNIT_PAUSE_IN_SECONDS * pauseUnitCount);
        } catch (Exception ignored) {
        }
    }
}
