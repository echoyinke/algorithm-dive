package com.keyin.spring.config;

import com.keyin.spring.service.UserService;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: yinke
 * @create: 2021-05-06 17:36
 **/
public class AppConfig {
    
    @Bean
    public UserService userService() {
        return new UserService();
    }
}
