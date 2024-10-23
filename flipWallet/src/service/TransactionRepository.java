package service;

import enums.TransactionType;
import model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TransactionRepository {

    private final HashMap<String, List<Transaction>> userTransactionsMap;

    public TransactionRepository() {
        this.userTransactionsMap = new HashMap<>();
    }

    public void save(String senderName, Transaction sendTransaction) {
        List<Transaction> transactions = userTransactionsMap.get(senderName);
        if(Objects.isNull(transactions)){
            transactions = new ArrayList<>();
        }
        transactions.add(sendTransaction);
        userTransactionsMap.put(senderName, transactions);
    }

    public List<Transaction> findByNameAndTransactionType(String userName, TransactionType transactionType) {
        if(!userTransactionsMap.containsKey(userName)){
            return null;
        }
        List<Transaction> transactions = userTransactionsMap.get(userName);
        List<Transaction> result = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.getTransactionType().equals(transactionType)){
                result.add(transaction);
            }
        }
        return result;
    }

    public List<Transaction> findByName(String userName) {
        return userTransactionsMap.get(userName);
    }
}
