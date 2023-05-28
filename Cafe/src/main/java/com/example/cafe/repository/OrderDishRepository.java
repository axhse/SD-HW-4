package com.example.cafe.repository;

import com.example.cafe.model.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Specifies OrderDish entity repository
 */
@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {

    /**
     * Finds all Order Dish entities by Order Id
     * @param orderId order id
     * @return list of found Order Dish entities
     */
    List<OrderDish> findAllByOrderId(Long orderId);
}
