package service;

import java.math.BigDecimal;

public class DCPaymentStrategyImpl implements PaymentStrategy {
    @Override
    public Boolean withdrawMoney(String userName, BigDecimal amount) {
        return true;
    }
}
