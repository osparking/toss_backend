package com.toss_spring.backend.controller;

import com.toss_spring.backend.request.SaveAmountReq;
import com.toss_spring.backend.util.OrderIdPrefixGen;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

    @GetMapping("/checkAmount")
    public ResponseEntity<Boolean> getAmountFromSession(
            HttpSession session, @RequestBody SaveAmountReq request) {

        Object foundAmount = session.getAttribute(request.getOrderId());
        BigDecimal amount = (BigDecimal) foundAmount;
        Boolean result = request.getAmount().equals(amount);
        return ResponseEntity.ok(result);
    }

    private HttpURLConnection createConnection(
            String secretKey, String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", "Basic " +
                Base64.getEncoder().encodeToString(
                        (secretKey + ":").getBytes(StandardCharsets.UTF_8)));
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        return connection;
    }
}
