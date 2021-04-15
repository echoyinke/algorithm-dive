package com.keyin.javalang.annotatioin;

import lombok.AllArgsConstructor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @description:
 * @author: yinke
 * @create: 2021-03-30 18:06
 **/

@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String userName() default "yinke";
    
}
