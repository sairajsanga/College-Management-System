package com.project.uber.strategies;

import com.project.uber.entities.Payment;

public interface PaymentStrategy {
    Double PLATFORM_FEE = 0.3;
    void processPayment(Payment payment);

}
