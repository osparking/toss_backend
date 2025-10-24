package com.toss_spring.backend.entity;

import com.toss_spring.backend.util.CardInfo;
import com.toss_spring.backend.util.TossPaymentMethod;
import com.toss_spring.backend.util.TossPaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId",
            nullable = false)
    private BsOrder order;

    @Column(nullable = false)
    private LocalDateTime requestedAt;
    private String currency; // "KRW"

    @Column(nullable = false, unique = true)
    private String paymentKey;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TossPaymentMethod method; // "카드"를 CARD로 바꿔야 됨
    /**
     * 아마 confirm 호출한 시각
     */
    private LocalDateTime approvedAt;

    private String version; // "2022-11-16"
    private String receiptUrl; // url: https://...tosspayments.com/...

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "checkout_url"))
    private CardInfo card;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TossPaymentStatus status; // "DONE" 등

    /**
     * 금액 멤버들
     */
    private BigDecimal suppliedAmount; // 9,727
    private BigDecimal vat; // 973
    private BigDecimal balanceAmount; // 10,700
    private BigDecimal totalAmount; // 10,700
}