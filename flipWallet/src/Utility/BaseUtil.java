package Utility;

public class BaseUtil {
    private static Long userId = 0L;
    private static Long walletId = 0L;

    public static Long generateUserId(){
        return userId++;
    }

    public static Long generateWalletId(){
        return walletId++;
    }
}
