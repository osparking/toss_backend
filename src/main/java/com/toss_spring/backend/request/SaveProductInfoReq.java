package com.toss_spring.backend.request;

import com.toss_spring.backend.dto.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductInfoReq {
    private String orderId;
    private BigDecimal amount;
    private String productName;
}
