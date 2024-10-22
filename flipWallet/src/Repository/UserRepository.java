package Repository;

import model.User;
import Utility.BaseUtil;

import java.util.HashMap;

public class UserRepository {

    private final HashMap<String, User> registeredUser;

    public UserRepository(){
        registeredUser = new HashMap<>();
    }

    public User registerUser(String userName){
        if(registeredUser.containsKey(userName)){
            System.out.println("Already has a user with name: " + userName);
            return null;
        }
        User user = new User();
        user.setUserName(userName);
        user.setUserId(BaseUtil.generateUserId());
        registeredUser.put(userName, user);
        return user;
    }
}
