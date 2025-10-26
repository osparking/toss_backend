package com.toss_spring.backend.dto;

import com.toss_spring.backend.util.TossPaymentMethod;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
    private BigDecimal totalAmount;
    private TossPaymentMethod method;
    private String receiptUrl;
    private String paymentKey;
    private String orderId;
    private String orderName;
}
