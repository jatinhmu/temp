package service;

import enums.SortingType;
import enums.TransactionType;
import model.Transaction;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Boolean recordTransaction(String senderName, String recipientName, BigDecimal amount, Long
            timeOfTransaction) {
        if (Objects.isNull(senderName)) {
            return null;
        }
        if (Objects.isNull(recipientName)) {
            return null;
        }

        Transaction sendTransaction = getTransaction(senderName, recipientName, amount, timeOfTransaction, TransactionType.SEND);
        transactionRepository.save(senderName, sendTransaction);
        Transaction receiveTransaction = getTransaction(senderName, recipientName, amount, timeOfTransaction, TransactionType.RECEIVE);
        transactionRepository.save(recipientName, receiveTransaction);
        return true;
    }

    private Transaction getTransaction(String senderName, String recipientName, BigDecimal amount, Long timeOfTransaction, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setSenderName(senderName);
        transaction.setRecipientName(recipientName);
        transaction.setAmount(amount);
        transaction.setTransactionTimeStamp(timeOfTransaction);
        transaction.setTransactionType(transactionType);
        return transaction;
    }

    public List<Transaction> getTransactions(String userName, TransactionType transactionType, String sortingType) {
        if (Objects.isNull(userName)) {
            System.out.println("User: " + userName + " does not exist while getting transactions");
            return null;
        }
        if (Objects.isNull(sortingType)) {
            System.out.println("sortingType: " + sortingType + " does not exist while getting transactions");
            return null;
        }
        List<Transaction> allTransactions;
        if(transactionType==null){
            allTransactions = transactionRepository.findByName(userName);
        }else
            allTransactions = transactionRepository.findByNameAndTransactionType(userName, transactionType);
        if (allTransactions==null){
            System.out.println("No transactions found for user: " + userName);
            return null;
        }
        if (SortingType.AMOUNT.toString().equals(sortingType)) {
            allTransactions.sort(Comparator.comparing(Transaction::getAmount));
        } else if (SortingType.TIME.toString().equals(sortingType)) {
            allTransactions.sort(Comparator.comparing(Transaction::getTransactionTimeStamp));
        }

        for(Transaction t : allTransactions){
            System.out.println(t.getSenderName() + " -> " + t.getRecipientName() + " : " + t.getAmount() + "Rs");
        }

        return allTransactions;

    }
}
