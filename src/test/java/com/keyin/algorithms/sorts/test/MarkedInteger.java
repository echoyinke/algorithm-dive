package com.keyin.algorithms.sorts.test;

public class MarkedInteger {
    public Integer num;
    public String label;

    public MarkedInteger(Integer num, String label) {
        this.num = num;
        this.label = label;
    }

    @Override
    public String toString() {
        return num +"  "+ label;
    }

}
