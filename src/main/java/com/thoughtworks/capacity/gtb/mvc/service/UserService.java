package com.thoughtworks.capacity.gtb.mvc.service;

import com.thoughtworks.capacity.gtb.mvc.entity.User;
import com.thoughtworks.capacity.gtb.mvc.exception.UserExistException;
import com.thoughtworks.capacity.gtb.mvc.exception.UserLoginException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    ArrayList<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User(1,"liyuan", "pass123" , "yuan.li1@thoughtworks.com"));
    }

    public void register(User newUser) {
        if (isUserExist(newUser.getUsername())) {
            throw new UserExistException("用户已存在");
        }
        users.add(newUser);
    }

    public boolean isUserExist(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).count() > 0;
    }

    public User login(String username,String password){
        if (findUser(username) == null || findUser(username).getPassword().equals(password)) {
            throw new UserLoginException("用户名或密码错误");
        }
        User user = findUser(username);
        return user;
    }

    public User findUser(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().get();
    }
}
