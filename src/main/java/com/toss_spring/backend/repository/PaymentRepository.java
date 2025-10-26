package com.toss_spring.backend.repository;

import com.toss_spring.backend.dto.PaymentDto;
import com.toss_spring.backend.entity.TossPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<TossPayment, Long> {
    @Query(value = "SELECT new com.toss_spring.backend.dto.PaymentDto(" +
            "tp.id, " +
            "tp.approvedAt, " +
            "tp.totalAmount, " +
            "tp.method, " +
            "tp.receiptUrl, " +
            "tp.paymentKey, " +
            "bo.orderId, " +
            "bo.orderName) " +
            "FROM TossPayment tp " +
            "JOIN tp.order bo " +
            "ORDER BY tp.approvedAt DESC LIMIT :count")
    List<PaymentDto> getRecentSome(@Param("count") int count);
}
