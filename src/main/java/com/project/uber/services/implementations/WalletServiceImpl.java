package com.project.uber.services.implementations;

import com.project.uber.entities.Ride;
import com.project.uber.entities.User;
import com.project.uber.entities.Wallet;
import com.project.uber.entities.WalletTransaction;
import com.project.uber.entities.enums.TransactionMethod;
import com.project.uber.entities.enums.TransactionType;
import com.project.uber.exceptions.ResourceNotFoundException;
import com.project.uber.repositories.WalletRepository;
import com.project.uber.services.WalletService;
import com.project.uber.services.WalletTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;

    public WalletServiceImpl(WalletRepository walletRepository, WalletTransactionService walletTransactionService) {
        this.walletRepository = walletRepository;
        this.walletTransactionService = walletTransactionService;
    }

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        Double prevAmount = wallet.getBalance();
        Double newAmount = prevAmount + amount;
        wallet.setBalance(newAmount);

        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .transactionId(transactionId)
                .transactionMethod(transactionMethod)
                .transactionType(TransactionType.CREDIT)
                .ride(ride)
                .amount(amount)
                .wallet(wallet)
                .build();
        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        Double prevAmount = wallet.getBalance();
        Double newAmount = prevAmount + amount;
        wallet.setBalance(newAmount);

        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .transactionId(transactionId)
                .transactionMethod(transactionMethod)
                .transactionType(TransactionType.CREDIT)
                .amount(amount)
                .wallet(wallet)
                .build();
        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet subtractMoneyFromWallet(User user, Double amount, String transactionId, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        Double prevAmount = wallet.getBalance();
        Double newAmount = prevAmount - amount;
        wallet.setBalance(newAmount);

        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .transactionId(transactionId)
                .transactionMethod(transactionMethod)
                .transactionType(TransactionType.DEBIT)
                .amount(amount)
                .wallet(wallet)
                .build();

        wallet.getTransactions().add(walletTransaction);
        return walletRepository.save(wallet);

    }

    @Override
    @Transactional
    public Wallet subtractMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        Double prevAmount = wallet.getBalance();
        Double newAmount = prevAmount - amount;
        wallet.setBalance(newAmount);

        WalletTransaction walletTransaction = WalletTransaction
                .builder()
                .transactionId(transactionId)
                .transactionMethod(transactionMethod)
                .transactionType(TransactionType.DEBIT)
                .ride(ride)
                .amount(amount)
                .wallet(wallet)
                .build();

        wallet.getTransactions().add(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository
                .findById(walletId)
                .orElseThrow(()-> new ResourceNotFoundException("No wallet was found with ID: "+walletId));
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository
                .findByUser(user)
                .orElseThrow(()-> new ResourceNotFoundException("No wallet was found with ID: "+user.getId()));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(1000D);
        return walletRepository.save(wallet);
    }

    @Override
    public String generateTransactionId() {
        String uuid=UUID.randomUUID().toString().substring(0,8);
        String timeStamp=String.valueOf(System.currentTimeMillis());
        return "TRAN"+uuid+timeStamp;
    }
}
