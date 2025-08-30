package com.project.uber.services.implementations;

import com.project.uber.entities.Payment;
import com.project.uber.entities.Ride;
import com.project.uber.entities.enums.PaymentStatus;
import com.project.uber.exceptions.ResourceNotFoundException;
import com.project.uber.repositories.PaymentRepository;
import com.project.uber.services.PaymentService;
import com.project.uber.strategies.managers.PaymentStrategyManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Ride ride) {

        try {

            Payment payment = paymentRepository.findByRide(ride).orElseThrow(() -> new ResourceNotFoundException("Payment not found!"));
            paymentStrategyManager
                    .paymentStrategy(payment.getPaymentMethod())
                    .processPayment(payment);

            updatePaymentStatus(payment, PaymentStatus.CONFIRMED);
        }catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Payment createNewPayment(Ride ride) {

        Payment payment = Payment
                .builder()
                .amount(ride.getFare())
                .paymentMethod(ride.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .ride(ride)
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }
}
