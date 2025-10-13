package com.toss_spring.backend.controller;

import com.toss_spring.backend.request.SaveAmountReq;
import com.toss_spring.backend.util.OrderIdPrefixGen;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
public class PaymentCon {
  private static final Logger logger = LoggerFactory.getLogger(PaymentCon.class);

  @PostMapping("/saveAmount")
  public ResponseEntity<?> saveAmountTemporarily(
      HttpSession session, @RequestBody SaveAmountReq request) {

    var orderId = OrderIdPrefixGen.getOrderIdPrefix(6) + request.getOrderId();
    logger.info("orderId: " + orderId);
    session.setAttribute(orderId, request.getAmount());

    return ResponseEntity.ok("세션에 <주문 ID, 지불 금액> 항목을 저장함.");
  }
}
