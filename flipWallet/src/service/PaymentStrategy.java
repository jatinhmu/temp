package service;

import java.math.BigDecimal;

public interface PaymentStrategy {

    Boolean withdrawMoney(String userName, BigDecimal amount);
}
