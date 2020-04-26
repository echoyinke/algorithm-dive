package com.keyin.algorithms.stack;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x) -- 将元素 x 推入栈中。
 * pop() -- 删除栈顶的元素。
 * top() -- 获取栈顶元素。
 * getMin() -- 检索栈中的最小元素。
 *
 * **/
@Data
@NoArgsConstructor
public class Stack implements StackInterface {
     java.util.Stack stack = new java.util.Stack();
     Integer MIN = Integer.MAX_VALUE;


    public static void main(String[] args) {
        Stack minStack = new Stack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());;
        System.out.println(minStack.top());
        ;



    }

    @Override
    public void push(Integer integer) {
        stack.push(integer);
        if (integer <= MIN) {
            stack.push(integer);
        } else {
            stack.push(MIN);
        }
    }

    @Override
    public Integer pop() {
        stack.pop();
        return (Integer) stack.pop();
    }

    @Override
    public Integer top() {
        Integer min = (Integer) stack.pop();
        Integer top = (Integer) stack.peek();
        stack.push(min);
        return top;
    }

    @Override
    public boolean empty() {
        return false;
    }

    @Override
    public Integer getMin() {
        return (Integer) stack.peek();
    }
}
