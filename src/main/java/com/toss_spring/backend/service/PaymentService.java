package com.toss_spring.backend.service;

import com.toss_spring.backend.dto.PaymentDto;
import com.toss_spring.backend.entity.TossPayment;
import com.toss_spring.backend.exception.OrderIdNotFoundEx;
import com.toss_spring.backend.exception.PaymentArgException;
import com.toss_spring.backend.repository.PaymentRepository;
import com.toss_spring.backend.util.CardInfo;
import com.toss_spring.backend.util.TossPaymentMethod;
import com.toss_spring.backend.util.TossPaymentStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class PaymentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentDto> getRecentSome(int count) {
        return paymentRepository.getRecentSome(count);
    }

    public TossPayment createPayment(JSONObject paymentJSON)
            throws OrderIdNotFoundEx {

        var payment = new TossPayment();
        payment.setPaymentKey((String) paymentJSON.get("paymentKey"));

        /**
         * 일단 orderId 를 사용하여 bsOrder 객체를 읽고, 이를 배정한다.
         */
        String orderId = (String) paymentJSON.get("orderId");
        payment.setOrder(orderService.getOrderByOrderId(orderId));

        payment.setSuppliedAmount(BigDecimal.valueOf(
                (Long) paymentJSON.get("suppliedAmount")));

        var requestedAtODT = OffsetDateTime.parse(
                (String) paymentJSON.get("requestedAt"));
        payment.setRequestedAt(requestedAtODT.toLocalDateTime());

        payment.setCurrency((String) paymentJSON.get("currency"));

        payment.setPaymentKey((String) paymentJSON.get("paymentKey"));

        var method = TossPaymentMethod
                .valueOfLabel((String) paymentJSON.get("method"));
        payment.setMethod(method);

        payment.setVat(BigDecimal.valueOf((Long) paymentJSON.get("vat")));

        var approvedAtODT = OffsetDateTime.parse(
                (String) paymentJSON.get("approvedAt"));
        payment.setApprovedAt(approvedAtODT.toLocalDateTime());

        payment.setBalanceAmount(BigDecimal.valueOf(
                (Long) paymentJSON.get("balanceAmount")));
        payment.setVersion((String) paymentJSON.get("version"));

        payment.setTotalAmount(BigDecimal.valueOf(
                (Long) paymentJSON.get("totalAmount")));

        JSONObject recepitJSON = (JSONObject) paymentJSON.get("receipt");
        payment.setReceiptUrl((String) recepitJSON.get("url"));

        payment.setCard(getCardInfo((JSONObject) paymentJSON.get("card")));

        var status = TossPaymentStatus
                .valueOf((String) paymentJSON.get("status"));
        payment.setStatus(status);

        try {
            entityManager.persist(payment);
        } catch (IllegalArgumentException e) {
            throw new PaymentArgException(e.getMessage() + " - 결제정보");
        } finally {
            return null;
        }
    }

    private CardInfo getCardInfo(JSONObject cardJSON) {
        CardInfo card = new CardInfo();
        card.setCardOwnerType((String) cardJSON.get("ownerType"));
        card.setCardNumber((String) cardJSON.get("number"));
        card.setCardAmount(BigDecimal.valueOf((Long) cardJSON.get("amount")));
        card.setCardType((String) cardJSON.get("cardType"));
        card.setCardIssuerCode((String) cardJSON.get("issuerCode"));

        return card;
    }
}