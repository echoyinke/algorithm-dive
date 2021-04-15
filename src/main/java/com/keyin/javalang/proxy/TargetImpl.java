package com.keyin.javalang.proxy;

public class TargetImpl implements Target {

    @Override
    public int test(int i) {
        return i + 1;
    }
}