package com.toss_spring.backend.controller;

import com.toss_spring.backend.dto.OrderInfo;
import com.toss_spring.backend.request.SaveOrderInfoReq;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderCon {

    @PostMapping("/saveOrderInfo")
    public ResponseEntity<?> saveAmountTemporarily(
            HttpSession session, @RequestBody SaveOrderInfoReq request) {
        session.setAttribute(request.getOrderId(), new OrderInfo(
                request.getAmount(), request.getOrderName()));
        return ResponseEntity.ok("세션에 <주문 ID, 상품 정보> 항목을 저장함.");
    }
}
