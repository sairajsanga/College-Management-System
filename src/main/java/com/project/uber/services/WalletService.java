package com.project.uber.services;

import com.project.uber.entities.Ride;
import com.project.uber.entities.User;
import com.project.uber.entities.Wallet;
import com.project.uber.entities.enums.TransactionMethod;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet addMoneyToWallet(User user, Double amount, String transactionId, TransactionMethod transactionMethod);

    Wallet subtractMoneyFromWallet(User user,Double amount,String transactionId,TransactionMethod transactionMethod);

    Wallet subtractMoneyFromWallet(User user,Double amount,String transactionId,Ride ride,TransactionMethod transactionMethod);

    Wallet findWalletById(Long walletId);

    Wallet findByUser(User user);

    Wallet createNewWallet(User user);

    String generateTransactionId();
}
