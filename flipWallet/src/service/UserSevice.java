package service;

import Repository.UserRepository;
import model.User;

import java.util.Objects;

public class UserSevice {

    private final UserRepository userRepository;
    public UserSevice(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void registerUser(String name){
        if(Objects.isNull(name)){
            System.out.println("Name cannot be empty while registering");
        }
        if(userRepository.registerUser(name)!=null) {
            System.out.println("User: " + name + " registered Successfully");
        }
    }
}
