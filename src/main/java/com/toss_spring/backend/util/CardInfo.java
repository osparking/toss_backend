package com.toss_spring.backend.util;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CardInfo {
    private String ownerType; // "개인
    private String number; // "46731099****758*"
    private BigDecimal amount; // 10,700
    private String cardType; // "체크"
    private String issuerCode; // "11"
}
