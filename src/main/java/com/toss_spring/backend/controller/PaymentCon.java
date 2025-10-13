package com.toss_spring.backend.controller;

import com.toss_spring.backend.request.SaveAmountReq;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentCon {
  @PostMapping("/saveAmount")
  public ResponseEntity<?> saveAmountTemporarily(
      HttpSession session, @RequestBody SaveAmountReq request) {

    session.setAttribute(request.getOrderId(), request.getAmount());
    return ResponseEntity.ok("세션에 <주문 ID, 지불 금액> 항목을 저장함.");
  }
}
