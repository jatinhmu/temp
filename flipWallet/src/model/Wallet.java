package model;

import Utility.BaseUtil;

import java.math.BigDecimal;

public class Wallet {
    private Long walletId;
    private String userName;
    private BigDecimal currBalance;

    public Wallet(String username){
        walletId = BaseUtil.generateWalletId();
        userName = username;
        currBalance = BigDecimal.ZERO;

    }
    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public BigDecimal getCurrBalance(){
        return this.currBalance;
    }

    public void setCurrBalance(BigDecimal newBalance){
        this.currBalance = newBalance;
    }
}
