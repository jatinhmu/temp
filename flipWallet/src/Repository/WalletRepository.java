package Repository;

import model.Wallet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

public class WalletRepository {

    private final HashMap<String, Wallet> userWallet;

    public WalletRepository() {
        this.userWallet = new HashMap<>();
    }

    public Boolean topUpWallet(String userName, BigDecimal amount) {
        Wallet wallet = userWallet.get(userName);

        if (Objects.isNull(wallet)) {
            wallet = new Wallet(userName);
        }

        BigDecimal currBalance = wallet.getCurrBalance();

        if (Objects.isNull(currBalance)) {
            currBalance = new BigDecimal(0);
        }

        System.out.println("User: " + userName + " TopUp wallet, increasing amount from " + currBalance + " to " +
                currBalance.add(amount));


        currBalance = currBalance.add(amount);
        wallet.setCurrBalance(currBalance);
        this.userWallet.put(userName, wallet);
        return true;
    }

    public BigDecimal fetchBalance(String userName) {
        if (!userWallet.containsKey(userName)) {
            System.out.println("userName: " + userName + " cannot be null  while topup");
            return null;
        }

        Wallet wallet = this.userWallet.get(userName);
        return wallet.getCurrBalance();
    }

    public Boolean sendMoney(String senderName, String recipientName, BigDecimal amount) {
        Wallet senderWallet = userWallet.get(senderName);
        if (Objects.isNull(senderWallet)) {
            senderWallet = new Wallet(senderName);
        }
        BigDecimal senderBalance = senderWallet.getCurrBalance();

        if (senderBalance.compareTo(amount) < 0) {
            System.out.println("sender does not have enough money");
            return false;
        }

        BigDecimal senderUpdatedBalance = senderBalance.subtract(amount);
        senderWallet.setCurrBalance(senderUpdatedBalance);
        userWallet.put(senderName, senderWallet);

        Wallet recipientWallet = userWallet.get(recipientName);
        if (Objects.isNull(recipientWallet)) {
            recipientWallet = new Wallet(recipientName);
        }

        BigDecimal recipientBalance = recipientWallet.getCurrBalance();
        BigDecimal recipientUpdatedBalance = recipientBalance.add(amount);
        recipientWallet.setCurrBalance(recipientUpdatedBalance);

        userWallet.put(recipientName, recipientWallet);
        System.out.println(senderName + " has transferred " + amount + " to " + recipientName);
        return true;
    }
}
