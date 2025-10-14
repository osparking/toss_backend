package com.toss_spring.backend.util;

import com.toss_spring.backend.request.ConfirmPaymentReq;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PayServiceTest {
    @Test
    void testRequestToJsonConversion() {
        ConfirmPaymentReq req = new ConfirmPaymentReq(
                "abcd", "orderId-01", BigDecimal.valueOf(50000)
        );
        String result = PayService.convertToJson(req);
        System.out.println(result);
        assertNotNull(result);
    }
}