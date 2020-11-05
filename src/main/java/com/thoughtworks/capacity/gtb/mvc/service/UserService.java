package com.thoughtworks.capacity.gtb.mvc.service;

import com.thoughtworks.capacity.gtb.mvc.entity.User;
import com.thoughtworks.capacity.gtb.mvc.exception.UserExistException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    ArrayList<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("liyuan", "pass123" , "yuan.li1@thoughtworks.com"));
    }

    public void register(User newUser) {
        if (users.stream().filter(user -> user.getUsername().equals(newUser.getUsername())).count() > 0) {
            throw new UserExistException("用户已存在");
        }
        users.add(newUser);
    }
}
