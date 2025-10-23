package com.toss_spring.backend.service;

import com.toss_spring.backend.entity.BsOrder;
import com.toss_spring.backend.util.OrderStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class OrderIdGeneratorTest {

    @Autowired
    private OrderIdGenerator orderIdGenerator;

    @Test
    public void testGeneratedOrderId() {
        BsOrder order = new BsOrder();
        order.setOrderId("");
        order.setAmount(BigDecimal.ONE);
        order.setPayWaitTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PAY_WAIT);

        var orderId = orderIdGenerator.generateOrderId(order);
        System.out.println(orderId);

        assertNotNull(orderId);
    }
}