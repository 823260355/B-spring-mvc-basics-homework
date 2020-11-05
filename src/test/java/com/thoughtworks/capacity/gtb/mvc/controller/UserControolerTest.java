package com.thoughtworks.capacity.gtb.mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capacity.gtb.mvc.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControolerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_register_user_is_success() throws Exception {
        User user = new User("liyuan1", "pass123", "yuan.li1@thoughtworks.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_register_is_fail_when_username_illegal() throws Exception {
        User newUser = new User("li", "pass123", "yuan.li1@thoughtworks.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post("/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("用户名不合法")));

        User newUser2 = new User("liyuan#*", "pass123", "yuan.li1@thoughtworks.com");
        String json2 = objectMapper.writeValueAsString(newUser2);
        mockMvc.perform(post("/register").content(json2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("用户名不合法")));

        User newUser3 = new User("", "pass123", "yuan.li1@thoughtworks.com");
        String json3 = objectMapper.writeValueAsString(newUser3);
        mockMvc.perform(post("/register").content(json3).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("用户名不合法")));
    }

    @Test
    void should_register_is_fail_when_password_illegal () throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User newUser = new User("liyuan", "23", "yuan.li1@thoughtworks.com");
        String json = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post("/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("密码不合法")));
    }

    @Test
    void should_register_is_fail_when_email_illegal() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User newUser = new User("liyuan", "pass123", "houghtworks.com");
        String json = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post("/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("邮箱地址不合法")));
    }

    @Test
    void should_register_is_fail_if_user_exists () throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User newUser = new User("liyuan", "pass123", "yuan.li1@thoughtworks.com");
        String json = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post("/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("用户已存在")));
    }
}