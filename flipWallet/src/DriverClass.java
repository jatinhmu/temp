import Repository.UserRepository;
import Repository.WalletRepository;
import enums.PaymentMode;
import enums.TransactionType;
import service.TransactionRepository;
import service.TransactionService;
import service.UserSevice;
import service.WalletService;

import java.math.BigDecimal;

public class DriverClass {

    public static void main(String[] args){

        UserRepository userRepository = new UserRepository();
        WalletRepository walletRepository = new WalletRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        UserSevice userSevice = new UserSevice(userRepository);
        TransactionService transactionService = new TransactionService(transactionRepository);
        WalletService walletService = new WalletService(walletRepository, transactionService);

        userSevice.registerUser("Anmol");
        walletService.topupWallet("Anmol", PaymentMode.CC, BigDecimal.valueOf(12.12));

        userSevice.registerUser("Sid");

        userSevice.registerUser("Divya");

        walletService.fetchBalance("Anmol");

        walletService.sendMoney("Anmol","Sid", BigDecimal.valueOf(5.0));

        walletService.sendMoney("Anmol","Divya", BigDecimal.valueOf(2.0));

        walletService.sendMoney("Divya","Sid", BigDecimal.valueOf(2.0));

        walletService.fetchBalance("Sid");

        walletService.fetchBalance("Anmol");

        transactionService.getTransactions("Anmol", TransactionType.SEND,"AMOUNT");

        transactionService.getTransactions("Divya", TransactionType.SEND,"AMOUNT");

        transactionService.getTransactions("Sid", TransactionType.RECEIVE,"AMOUNT");

    }
}
