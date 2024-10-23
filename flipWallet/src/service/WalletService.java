package service;

import Repository.*;
import enums.PaymentMode;

import java.math.BigDecimal;
import java.util.Objects;

public class WalletService {

    public final WalletRepository walletRepository;
    public final TransactionService transactionService;

    public WalletService(WalletRepository walletRepository,
                         TransactionService transactionService) {
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
    }


    public void topupWallet(String userName, PaymentMode paymentMode, BigDecimal amount){
        if(Objects.isNull(userName)){
            System.out.println("userName is null");
            return;
        }
        if(Objects.isNull(paymentMode)){
            System.out.println("paymentMode is null");
            return;
        }
        if(Objects.isNull(amount)){
            System.out.println("amount is null");
            return;
        }

        PaymentStrategy paymentStrategy = getPaymentStrategy(paymentMode);
        if(paymentStrategy == null){
            System.out.println("invalid payment strategy");
            return;
        }
        Boolean moneyWithdrawn = paymentStrategy.withdrawMoney(userName, amount);

        if(Boolean.FALSE.equals(moneyWithdrawn)){
            System.out.println("Unable to withdraw money from payment source: " + paymentMode);
        }

        walletRepository.topUpWallet(userName, amount);
    }

    private PaymentStrategy getPaymentStrategy(PaymentMode paymentMode) {
        if(paymentMode.equals(PaymentMode.CC)){
            return new CCPaymentStrategyImpl();
        }
        if(paymentMode.equals(PaymentMode.DC)){
            return new DCPaymentStrategyImpl();
        }
        if (PaymentMode.UPI.equals(paymentMode)) {
            return new UpiProviderDaoImpl();
        }
        return null;
    }

    public void fetchBalance(String userName){
        if(Objects.isNull(userName)){
            //todo
        }

        BigDecimal currBalance = walletRepository.fetchBalance(userName);
        System.out.println("CurrBalance of user: " + userName + " is  " + currBalance);
        return;
    }

    public void sendMoney(String senderName, String recipientName, BigDecimal amount){
        if(Objects.isNull(senderName)){
            System.out.println("senderName is null");
            return;
        }
        if(Objects.isNull(recipientName)){
            System.out.println("recipientName is null");
            return;
        }
        if(Objects.isNull(amount)){
            System.out.println("amount is null");
            return;
        }

        Long transactionTime = System.currentTimeMillis();

        if(amount.compareTo(BigDecimal.valueOf(0)) <= 0){
            System.out.println("amount should be greater than zero");
        }

        Boolean isTransferSuccess = walletRepository.sendMoney(senderName, recipientName, amount);

        if (!isTransferSuccess){
            System.out.println("Unable to send money from user: " + senderName + " to recipient: " + recipientName);
            return;
        }
        transactionService.recordTransaction(senderName, recipientName, amount, transactionTime);
        cashBack(senderName, amount);
    }

    private void cashBack(String senderName, BigDecimal amount) {
        double random = Math.random()*10;
        if(random < 0.5)return;
//        walletRepository.addMoney(senderName, amount);

    }
}
