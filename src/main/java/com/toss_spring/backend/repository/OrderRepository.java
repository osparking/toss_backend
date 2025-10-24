package com.toss_spring.backend.repository;

import com.toss_spring.backend.entity.BsOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<BsOrder, Long> {
    Optional<BsOrder> findByOrderId(String orderId);
}