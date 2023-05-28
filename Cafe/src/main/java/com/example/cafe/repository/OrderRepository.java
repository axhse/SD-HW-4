package com.example.cafe.repository;

import com.example.cafe.model.CafeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Specifies Order entity repository
 */
@Repository
public interface OrderRepository extends JpaRepository<CafeOrder, Long> {
}
