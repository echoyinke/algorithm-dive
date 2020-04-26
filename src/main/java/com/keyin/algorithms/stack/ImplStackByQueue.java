package com.keyin.algorithms.stack;

import java.util.ArrayList;
import java.util.LinkedList;

public class ImplStackByQueue implements StackInterface {
    static LinkedList<Integer> queue = new LinkedList<>();
    static LinkedList<Integer> tmpQ = new LinkedList<>();
    static Integer top;


    @Override
    public void push(Integer integer) {
        queue.add(integer);
        top = integer;
    }

    @Override
    public Integer pop() {

        while (queue.size()>1) {
            top = queue.poll();

            tmpQ.add(queue.poll());
        }
        Integer res = queue.poll();
        queue = tmpQ;
        tmpQ.clear();
        return res;
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
