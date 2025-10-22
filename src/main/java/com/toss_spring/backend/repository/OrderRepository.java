package com.toss_spring.backend.repository;

import com.toss_spring.backend.entity.BsOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<BsOrder, Long> {
}
