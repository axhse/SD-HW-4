package com.example.cafe.repository;

import com.example.cafe.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Specifies Dish entity repository
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
}
