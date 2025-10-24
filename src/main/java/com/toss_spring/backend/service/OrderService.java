package com.toss_spring.backend.service;

import com.toss_spring.backend.entity.BsOrder;
import com.toss_spring.backend.exception.OrderIdNotFoundEx;
import com.toss_spring.backend.repository.OrderRepository;
import com.toss_spring.backend.util.Feedback;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderIdGenerator orderIdGenerator;

    @Autowired
    private OrderRepository orderRepository;

    public BsOrder getOrderByOrderId(String orderId)
            throws OrderIdNotFoundEx {
        var optionalOrder = orderRepository.findByOrderId(orderId);

        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            throw new OrderIdNotFoundEx(Feedback.UNFOUND_ORDER_ID + orderId);
        }
    }

    public BsOrder createOrder(BsOrder order) {
        entityManager.persist(order);
        entityManager.flush();
        // Generate orderId with guaranteed ID
        String orderId = orderIdGenerator.generateOrderId(order);
        order.setOrderId(orderId);

        // No need to save again - JPA will auto-update during commit
        return order;
    }
}