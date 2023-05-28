package com.example.cafe.controller;

import com.example.cafe.api.message.*;
import com.example.cafe.api.model.*;
import com.example.cafe.api.params.*;
import com.example.cafe.model.CafeOrder;
import com.example.cafe.model.Dish;
import com.example.cafe.model.OrderDish;
import com.example.cafe.model.UserRole;
import com.example.cafe.service.CafeService;
import com.example.cafe.service.UserManagerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Implements Cafe API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe")
public class CafeController {
    private final CafeService cafeService;
    private final UserManagerClient userClient;

    /**
     * Cancels order
     * @param authHeaderValue authentication header value
     * @param orderId order id
     */
    @PostMapping("/order/cancel")
    public ResponseEntity<Message> cancelOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeaderValue,
                                               Long orderId) {
        var user = userClient.getUserData(authHeaderValue);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        var order = cafeService.findOrder(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        if (!order.getUserId().equals(user.getId()) && UserRole.isCustomer(user.getRole())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        order.setStatus(CafeOrder.STATUS_CANCELED);
        cafeService.saveOrder(order);
        return ResponseEntity.ok(new Message("Canceled"));
    }

    /**
     * Creates a dish
     * @param authHeaderValue authentication header value
     * @param params dish creation params
     */
    @PostMapping("/dish/create")
    public ResponseEntity<Message> createDish(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeaderValue,
                                              DishParams params) {
        var user = userClient.getUserData(authHeaderValue);
        if (user == null || !UserRole.isManager(user.getRole())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (params == null) {
            return ResponseEntity.badRequest().body(new Message("Params are missed"));
        }
        if (!Dish.isValidName(params.getName())) {
            return ResponseEntity.badRequest().body(new Message("Invalid name value"));
        }
        if (!Dish.isValidDescription(params.getDescription())) {
            return ResponseEntity.badRequest().body(new Message("Invalid description value"));
        }
        if (!Dish.isValidPrice(params.getPrice())) {
            return ResponseEntity.badRequest().body(new Message("Invalid price value"));
        }
        if (!Dish.isValidQuantity(params.getQuantity())) {
            return ResponseEntity.badRequest().body(new Message("Invalid quantity value"));
        }
        if (!Dish.isValidIsAvailable(params.getIsAvailable())) {
            return ResponseEntity.badRequest().body(new Message("Invalid isAvailable value"));
        }
        var dish = new Dish(null, params.getName(), params.getDescription(), params.getPrice(),
                            params.getQuantity(), params.getIsAvailable(), null, null);
        var savedDish = cafeService.saveDish(dish);
        return new ResponseEntity<>(new DishCreationMessage(savedDish.getId()), HttpStatus.CREATED);
    }

    /**
     * Creates an order
     * @param authHeaderValue authentication header value
     * @param params order creation params
     */
    @PostMapping("/order/create")
    public ResponseEntity<Message> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeaderValue,
                                               @RequestBody OrderParams params) {
        var user = userClient.getUserData(authHeaderValue);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (params == null) {
            return ResponseEntity.badRequest().body(new Message("Params are missed"));
        }
        if (!CafeOrder.isValidSpecialRequests(params.getSpecialRequests())) {
            return ResponseEntity.badRequest().body(new Message("Invalid specialRequests value"));
        }
        if (params.dishes.size() < 1 || CafeOrder.MAX_ORDER_SIZE < params.dishes.size()) {
            return ResponseEntity.badRequest().body(new Message("Unexpected dish count"));
        }
        for (var dishParams : params.getDishes()) {
            if (!Dish.isValidQuantity(dishParams.getQuantity()) || dishParams.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body(new Message("Invalid quantity value"));
            }
        }
        var allOrderDish = new ArrayList<OrderDish>();
        for (int paramIndex = 0; paramIndex < params.getDishes().size(); paramIndex++) {
            var dishParams = params.getDishes().get(paramIndex);
            var quantityParam = dishParams.getQuantity();
            var dish = cafeService.findDish(dishParams.getId());
            if (dish == null || !dish.canBeOrdered() || dish.getQuantity() < quantityParam) {
                for (int otherIndex = paramIndex - 1; otherIndex >= 0; otherIndex--) {
                    var otherDish = cafeService.findDish(dishParams.getId());
                    if (otherDish != null) {
                        otherDish.setQuantity(otherDish.getQuantity() + quantityParam);
                        cafeService.saveDish(otherDish);
                    }
                }
                var message = new Message("Dish id=" + dishParams.getId().toString() + " can not be ordered");
                return ResponseEntity.badRequest().body(message);
            }
            dish.setQuantity(dish.getQuantity() - dishParams.getQuantity());
            cafeService.saveDish(dish);
            var price = dish.getPrice().multiply(new BigDecimal(quantityParam));
            var orderDish = new OrderDish(null, null, dish.getId(), quantityParam, price);
            allOrderDish.add(orderDish);
        }
        var order = new CafeOrder(null, user.getId(), CafeOrder.STATUS_CREATED,
                                  params.getSpecialRequests(), null, null);
        var savedOrder = cafeService.saveOrder(order);
        for (var orderDish : allOrderDish) {
            orderDish.setOrderId(savedOrder.getId());
        }
        if (!cafeService.trySaveAllOrderDish(allOrderDish)) {
            return ResponseEntity.badRequest().body(new Message("Some dishes are probably deleted"));
        }
        cafeService.runOrderCompletion(savedOrder.getId());
        return new ResponseEntity<>(new OrderCreationMessage(savedOrder.getId()), HttpStatus.CREATED);
    }

    /**
     * Deletes dish
     * @param authHeaderValue authentication header value
     * @param dishId dish id
     */
    @PostMapping("/dish/delete")
    public ResponseEntity<Message> deleteDish(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeaderValue,
                                              Long dishId) {
        var user = userClient.getUserData(authHeaderValue);
        if (user == null || !UserRole.isManager(user.getRole())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        cafeService.deleteDish(dishId);
        return ResponseEntity.ok(new Message("Deleted"));
    }

    /**
     * Returns dish information
     * @param authHeaderValue authentication header value
     * @param dishId dish id
     */
    @GetMapping("/dish/get")
    public ResponseEntity<DishData> getDish(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeaderValue,
                                            Long dishId) {
        var user = userClient.getUserData(authHeaderValue);
        if (user == null || !UserRole.isManager(user.getRole())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        var dish = cafeService.findDish(dishId);
        if (dish == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DishData(dish));
    }

    /**
     * Returns menu information
     * @param authHeaderValue authentication header value
     */
    @GetMapping("/menu")
    public ResponseEntity<Menu> getMenu(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeaderValue) {
        var user = userClient.getUserData(authHeaderValue);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        var menu = new Menu();
        var allDish = cafeService.findAllDish();
        for (var dish : allDish) {
            if (dish.canBeOrdered()) {
                menu.dishes.add(new DishData(dish));
            }
        }
        return ResponseEntity.ok(menu);
    }

    /**
     * Returns order information
     * @param authHeaderValue authentication header value
     * @param orderId order id
     */
    @GetMapping("/order/get")
    public ResponseEntity<OrderData> getOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeaderValue,
                                              Long orderId) {
        var user = userClient.getUserData(authHeaderValue);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        var order = cafeService.findOrder(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        if (!order.getUserId().equals(user.getId()) && UserRole.isCustomer(user.getRole())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        var orderData = new OrderData(order);
        var allOrderDish = cafeService.findAllOrderDish(orderId);
        for (var orderDish : allOrderDish) {
            var dish = cafeService.findDish(orderDish.getDishId());
            orderData.dishes.add(new OrderDishData(orderDish, dish));
        }
        return ResponseEntity.ok(orderData);
    }

    /**
     * Updates order
     * @param authHeaderValue authentication header value
     * @param params order updating params
     */
    @PostMapping("/dish/update")
    public ResponseEntity<Message> updateDish(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeaderValue,
                                              DishParams params) {
        var user = userClient.getUserData(authHeaderValue);
        if (user == null || !UserRole.isManager(user.getRole())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (params == null) {
            return ResponseEntity.badRequest().body(new Message("Params are missed"));
        }
        if (params.getId() <= 0) {
            return ResponseEntity.badRequest().body(new Message("Invalid id value"));
        }
        if (!Dish.isValidName(params.getName())) {
            return ResponseEntity.badRequest().body(new Message("Invalid name value"));
        }
        if (!Dish.isValidDescription(params.getDescription())) {
            return ResponseEntity.badRequest().body(new Message("Invalid description value"));
        }
        if (!Dish.isValidPrice(params.getPrice())) {
            return ResponseEntity.badRequest().body(new Message("Invalid price value"));
        }
        if (!Dish.isValidQuantity(params.getQuantity())) {
            return ResponseEntity.badRequest().body(new Message("Invalid quantity value"));
        }
        if (!Dish.isValidIsAvailable(params.getIsAvailable())) {
            return ResponseEntity.badRequest().body(new Message("Invalid isAvailable value"));
        }
        var dish = cafeService.findDish(params.getId());
        if (dish == null) {
            return ResponseEntity.notFound().build();
        }
        dish.setName(params.getName());
        dish.setDescription(params.getDescription());
        dish.setPrice(params.getPrice());
        dish.setQuantity(params.getQuantity());
        dish.setIsAvailable(params.getIsAvailable());
        cafeService.saveDish(dish);
        return ResponseEntity.ok(new Message("Updated"));
    }
}
