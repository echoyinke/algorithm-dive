package com.keyin.javalang.annotatioin;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-02 16:36
 **/
@MyAnnotation
public class Demo {
    @MyAnnotation(userName = "hello")
    public String name;
    
    @MyAnnotation(userName = "hello")
    public void hello() {
        
    }
}
