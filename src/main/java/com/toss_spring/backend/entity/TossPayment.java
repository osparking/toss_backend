package com.toss_spring.backend.entity;

import com.toss_spring.backend.util.CardInfo;
import com.toss_spring.backend.util.TossPaymentMethod;
import com.toss_spring.backend.util.TossPaymentStatus;
import com.toss_spring.backend.util.UrlAddress;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TossPayment {
    @Id
    private byte[] paymentId;

    @Column(nullable = false, unique = true)
    private String paymentKey;

    // 토스내부에서 관리하는 별도의 orderId가 존재함
    @Column(nullable = false)
    private String tossOrderId;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId",
            nullable = false)
    private BsOrder order;


    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TossPaymentMethod tossPaymentMethod;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TossPaymentStatus tossPaymentStatus;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    /**
     * 아마 confirm 호출한 시각
     */
    private LocalDateTime approvedAt;

    private String currency; // "KRW"
    private String method; // "카드"

    private BigDecimal suppliedAmount; // 9,727
    private BigDecimal vat; // 973
    private BigDecimal balanceAmount; // 10,700
    private BigDecimal totalAmount; // 10,700

    @Embedded
    private UrlAddress receipt; // url: https://...tosspayments.com/...

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "receipt_url"))
    private UrlAddress checkout; // url: https://api.tosspayments.com/...

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "checkout_url"))
    private CardInfo card;
    private String status; // "DONE"
    private String version; // "2022-11-16"
}
