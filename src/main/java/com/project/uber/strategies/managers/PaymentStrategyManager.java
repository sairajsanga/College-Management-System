package com.project.uber.strategies.managers;

import com.project.uber.entities.enums.PaymentMethod;
import com.project.uber.strategies.PaymentStrategy;
import com.project.uber.strategies.implementations.CashPaymentStrategy;
import com.project.uber.strategies.implementations.WalletPaymentStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case CASH -> cashPaymentStrategy;
            case WALLET -> walletPaymentStrategy;
        };
    }
}
