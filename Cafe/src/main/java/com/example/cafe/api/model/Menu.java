package com.example.cafe.api.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * Menu response body data
 */
@Data
public class Menu {

    public ArrayList<DishData> dishes = new ArrayList<>();
}
