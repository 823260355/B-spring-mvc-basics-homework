package com.thoughtworks.capacity.gtb.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Size(min = 3, max = 10, message = "用户名不合法")
    @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "用户名不合法")
    @NotBlank(message = "用户名不为空")
    private String username;
    @Size(min = 5, max = 12, message = "密码不合法")
    @NotBlank(message = "密码是不为空")
    private String password;
    @Email(message = "邮箱地址不合法")
    private String email;
}
