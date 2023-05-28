package com.example.cafe.api.model;

import com.example.cafe.model.CafeOrder;
import lombok.Data;

import java.util.ArrayList;

/**
 * Order response body data
 */
@Data
public class OrderData {

    public OrderData(CafeOrder cafeOrder) {
        id = cafeOrder.getId();
        userId = cafeOrder.getUserId();
        status = cafeOrder.getStatus();
        specialRequests = cafeOrder.getSpecialRequests();
    }

    private Long id;

    private Long userId;

    private String status;

    private String specialRequests;

    public ArrayList<OrderDishData> dishes = new ArrayList<>();
}
