package com.keyin.algorithms.stack;


import java.util.Stack;

/**
 * 用栈实现队列
 * **/
public class ImplQueueByStack implements StackInterface {
        static Stack stackTmp = new java.util.Stack<>();
        static Stack stack = new Stack();

    @Override
    public void push(Integer integer) {
        stackTmp.push(integer);
        stack.push(integer);
    }

    @Override
    public Integer pop() {
        return null;
    }

    @Override
    public Integer top() {
        return null;
    }

    @Override
    public boolean empty() {
        return false;
    }

    @Override
    public Integer getMin() {
        return null;
    }
}
