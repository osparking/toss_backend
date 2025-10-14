package com.toss_spring.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toss_spring.backend.request.ConfirmPaymentReq;

public class PayService {
    public static String convertToJson(ConfirmPaymentReq req) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(req);
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            return null; // Or throw a more specific exception
        }
    }
}