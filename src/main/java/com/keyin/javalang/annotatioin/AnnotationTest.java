package com.keyin.javalang.annotatioin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-02 16:36
 **/


public class AnnotationTest {
    public static void main(String[] args) throws Exception {
        // 获取类上的注解
        Class<Demo> clazz = Demo.class;
        MyAnnotation annotationOnClass = clazz.getAnnotation(MyAnnotation.class);
        System.out.println(annotationOnClass.userName());

        // 获取成员变量上的注解
        Field name = clazz.getField("name");
        MyAnnotation annotationOnField = name.getAnnotation(MyAnnotation.class);
        System.out.println(annotationOnField.userName());

        // 获取hello方法上的注解
        Method hello = clazz.getMethod("hello", (Class<?>[]) null);
        MyAnnotation annotationOnMethod = hello.getAnnotation(MyAnnotation.class);
        System.out.println(annotationOnMethod.userName());

       
    }
}

