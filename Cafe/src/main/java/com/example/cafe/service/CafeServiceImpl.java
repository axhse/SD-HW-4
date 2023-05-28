package com.example.cafe.service;

import com.example.cafe.model.CafeOrder;
import com.example.cafe.model.Dish;
import com.example.cafe.model.OrderDish;
import com.example.cafe.repository.DishRepository;
import com.example.cafe.repository.OrderDishRepository;
import com.example.cafe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements service providing Dish, Order and Dish Order recording
 */
@Service
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {
    private final DishRepository dishRepository;
    private final OrderDishRepository orderDishRepository;
    private final OrderRepository orderRepository;
    private final OrderCompleter orderCompleter;

    public void deleteDish(Long dishId) { dishRepository.deleteById(dishId); }

    public List<Dish> findAllDish() {
        return dishRepository.findAll();
    }

    public List<OrderDish> findAllOrderDish(Long orderId) {
        return orderDishRepository.findAllByOrderId(orderId);
    }

    public Dish findDish(Long dishId) {
        return dishRepository.findById(dishId).orElse(null);
    }

    public CafeOrder findOrder(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void runOrderCompletion(Long orderId) {
        new Thread(() -> {
            orderCompleter.doOrder(orderId);
        }).start();
    }

    public Dish saveDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public CafeOrder saveOrder(CafeOrder order) {
        return orderRepository.save(order);
    }

    public Boolean trySaveAllOrderDish(ArrayList<OrderDish> allOrderDish) {
        try {
            orderDishRepository.saveAll(allOrderDish);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
