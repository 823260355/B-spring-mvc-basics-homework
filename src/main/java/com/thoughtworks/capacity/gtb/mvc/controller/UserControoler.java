package com.thoughtworks.capacity.gtb.mvc.controller;

import com.thoughtworks.capacity.gtb.mvc.entity.User;
import com.thoughtworks.capacity.gtb.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@Validated
public class UserControoler {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid User user){
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/login")
    public User login(@RequestParam
                          @Size(min = 3, max = 10, message = "用户名不合法")
                          @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "用户名不合法")
                          @NotBlank(message = "用户名不为空") String username,
                      @RequestParam
                          @Size(min = 5, max = 12, message = "密码不合法")
                          @NotBlank(message = "密码是不为空") String password) {
        User user = userService.login(username, password);
        return user;
    }
}
