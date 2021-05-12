package com.keyin.spring;

import com.keyin.spring.config.AppConfig;
import com.keyin.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: yinke
 * @create: 2021-05-06 17:35
 **/
public class ApplicationTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.test();
    }
}
