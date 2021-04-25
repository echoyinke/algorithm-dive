package com.keyin.javalang.enumtest;

public enum Fruit{
    APPLE(1),ORANGE(2),BANANA(3);
    int code;

    Fruit(int code){
        this.code=code;
    }
}