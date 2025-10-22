package com.toss_spring.backend.entity;

import com.toss_spring.backend.util.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BsOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * id 문자열(0 패딩, 8자리) + 6자리 문자열(영문대소자, 숫자, -, _)
     */
    @Column(nullable = false, unique = true)
    private String orderId;

    /**
     * 배송비 등 모두 포함한 최종 결제 금액
     */
    @Column(nullable = false)
    private BigDecimal amount;

    /**
     * 값의 의미: 주문 자료 저장 시각.
     * 저장 시각: 결제 대기(=PAY_WAIT) 상태 부여 일시.
     */
    @Column(name = "pay_wait_time", updatable = false, nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime payWaitTime = LocalDateTime.now();

    @Column(nullable = false)
    private OrderStatus orderStatus;

    /**
     * 생략된 정보: 주문 유저, 주문 항목, 수신처 등
     */
    private String productName;
}