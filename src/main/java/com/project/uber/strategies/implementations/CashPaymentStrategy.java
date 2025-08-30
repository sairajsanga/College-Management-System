package com.project.uber.strategies.implementations;

import com.project.uber.entities.Driver;
import com.project.uber.entities.Payment;
import com.project.uber.entities.enums.TransactionMethod;
import com.project.uber.services.WalletService;
import com.project.uber.strategies.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;

    @Override
    @Transactional
    public void processPayment(Payment payment) {

        Driver driver = payment.getRide().getDriver();
        double paymentCommission = payment.getAmount() * PLATFORM_FEE;

        walletService.subtractMoneyFromWallet(driver.getUser(),
                paymentCommission,
                walletService.generateTransactionId(), // Cash on delivery payments do not have transaction ids
                payment.getRide(),
                TransactionMethod.RIDE);
    }
}
