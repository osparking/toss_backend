package com.toss_spring.backend.repository;

import com.toss_spring.backend.entity.BsOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OrderCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void flush() {
        entityManager.flush(); // Forces immediate database synchronization
    }

    public void refresh(BsOrder order) {
        entityManager.refresh(order); // Reloads from database
    }
}
