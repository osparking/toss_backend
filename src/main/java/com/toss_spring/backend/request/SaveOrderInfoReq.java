package com.toss_spring.backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveOrderInfoReq {
    private String orderId;
    private BigDecimal amount;
    private String orderName;
}
