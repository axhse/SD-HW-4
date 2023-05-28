package com.example.cafe.api.params;

import lombok.Data;

import java.util.ArrayList;

/**
 * Order request params
 */
@Data
public class OrderParams {

    private String specialRequests;

    public ArrayList<OrderDishParams> dishes = new ArrayList<>();
}
