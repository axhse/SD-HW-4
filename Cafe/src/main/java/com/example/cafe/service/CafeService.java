package com.example.cafe.service;

import com.example.cafe.model.CafeOrder;
import com.example.cafe.model.Dish;
import com.example.cafe.model.OrderDish;

import java.util.ArrayList;
import java.util.List;

/**
 * Specifies service providing Dish, Order and Dish Order entities recording
 */
public interface CafeService {

    /**
     * Deletes Dish entity by Dish ID
     * @param dishId dish id
     */
    void deleteDish(Long dishId);

    /**
     * Finds all Dish entities
     * @return list of all Dish entities
     */
    List<Dish> findAllDish();

    /**
     * Finds all Order Dish entities by Order Id
     * @param orderId order id
     * @return list of all found Order Dish entities
     */
    List<OrderDish> findAllOrderDish(Long orderId);

    /**
     * Finds Dish entity by Dish ID
     * @param dishId dish id
     * @return found Dish entity
     */
    Dish findDish(Long dishId);

    /**
     * Finds Order entity by Order Id
     * @param orderId order id
     * @return found Order entity
     */
    CafeOrder findOrder(Long orderId);

    /**
     * Starts order completion in other thread
     * @param orderId id of order to be completed
     */
    void runOrderCompletion(Long orderId);

    /**
     * Saves Dish entity
     * @param dish Dish entity to save
     * @return saved Dish entity
     */
    Dish saveDish(Dish dish);

    /**
     * Saves Order entity
     * @param order Order entity to save
     * @return saved Order entity
     */
    CafeOrder saveOrder(CafeOrder order);

    /**
     * Saves list of Order Dish entities
     * @param allOrderDish list of Order Dish entities to save
     * @return true if all entities were saved, otherwise false
     */
    Boolean trySaveAllOrderDish(ArrayList<OrderDish> allOrderDish);
}
