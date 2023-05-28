package com.example.cafe.model;

import java.util.Arrays;

/**
 * Domain User roles
 */
public class UserRole {
    public static final String CHEF = "chef";
    public static final String CUSTOMER = "customer";
    public static final String MANAGER = "manager";
    public static final String[] ALL = {CHEF, CUSTOMER, MANAGER};

    /**
     * Checks whether string is a valid User role value
     * @param roleCode string value to be checked
     */
    public static Boolean isValidRole(String roleCode) {
        return Arrays.asList(ALL).contains(roleCode);
    }

    /**
     * Checks whether string role value is the Chef role
     * @param roleCode string value to be checked
     */
    public static Boolean isChef(String roleCode) {
        return CHEF.equals(roleCode);
    }

    /**
     * Checks whether string role value is the Customer role
     * @param roleCode string value to be checked
     */
    public static Boolean isCustomer(String roleCode) {
        return CUSTOMER.equals(roleCode);
    }

    /**
     * Checks whether string role value is the Manager role
     * @param roleCode string value to be checked
     */
    public static Boolean isManager(String roleCode) {
        return MANAGER.equals(roleCode);
    }
}
