package com.bookfair.backend.service;

import org.springframework.stereotype.Service;

import com.bookfair.backend.dto.payment.request.CreatePaymentRequest;
import com.bookfair.backend.exception.ErrorCode;
import com.bookfair.backend.exception.PaymentException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    public void processPayment(CreatePaymentRequest request) {
        try {
            // ... call Stripe API
        } catch (Exception e) {  //StripeException e
            // Wrap the Stripe exception in your custom PaymentException
            throw new PaymentException(
                "Payment declined by your bank", 
                e, 
                ErrorCode.PAYMENT_DECLINED
            );
        }
    }
}
