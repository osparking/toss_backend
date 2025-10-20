package com.toss_spring.backend.controller;

import com.toss_spring.backend.dto.ProductInfo;
import com.toss_spring.backend.request.ConfirmPaymentReq;
import com.toss_spring.backend.request.SaveProductInfoReq;
import com.toss_spring.backend.util.PayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/payments")
public class PaymentCon {
    private static final Logger logger = LoggerFactory.getLogger(PaymentCon.class);

    @PostMapping("/saveProductInfo")
    public ResponseEntity<?> saveAmountTemporarily(
            HttpSession session, @RequestBody SaveProductInfoReq request) {
        session.setAttribute(request.getOrderId(), new ProductInfo(
                request.getAmount(), request.getProductName()));
        return ResponseEntity.ok("세션에 <주문 ID, 상품 정보> 항목을 저장함.");
    }

    @GetMapping("/checkAmount")
    public ResponseEntity<Boolean> getAmountFromSession(
            HttpSession session,
            @RequestParam String orderId,
            @RequestParam BigDecimal amount) {

        Object foundAmount = session.getAttribute(orderId);
        Boolean result = amount.equals((BigDecimal) foundAmount);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = {"/confirm"})
    public ResponseEntity<JSONObject> confirmPayment(
            HttpServletRequest request,
            @RequestBody @NonNull ConfirmPaymentReq confirmPaymentReq)
            throws Exception {
        String confirmRequest = PayService.convertToJson(confirmPaymentReq);

        if (confirmRequest==null) {
            throw new Exception("결제 요청 객체 널 오류 발생");
        }
        JSONObject response = sendRequest(
                confirmRequest,
                System.getenv("WIDGET_SECRET_KEY"),
                "https://api.tosspayments.com/v1/payments/confirm");
        int statusCode = response.containsKey("error") ? 400:200;
        return ResponseEntity.status(statusCode).body(response);
    }

    private JSONObject sendRequest(String confirmStr, String secretKey,
                                   String urlString) throws IOException {
        HttpURLConnection connection = createConnection(secretKey, urlString);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(confirmStr.getBytes(StandardCharsets.UTF_8));
        }

        try (InputStream responseStream = connection.getResponseCode()==200 ?
                connection.getInputStream():
                connection.getErrorStream();
             Reader reader = new InputStreamReader(responseStream,
                     StandardCharsets.UTF_8)) {
            return (JSONObject) new JSONParser().parse(reader);
        } catch (Exception e) {
            logger.error("Error reading response", e);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Error reading response");
            return errorResponse;
        }
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
