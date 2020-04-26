package com.keyin.algorithms.math;

import java.util.Stack;

/**
 *
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.
 *
 * Example:
 *
 * Input: 38
 * Output: 2
 * Explanation: The process is like: 3 + 8 = 11, 1 + 1 = 2.
 *              Since 2 has only one digit, return it.
 * Follow up:
 * Could you do it without any loop/recursion in O(1) runtime?
 *
 * **/
public class AddDigits {

    public static final int addDigits(int target) {
       return (target - 1) % 9 + 1;


    }

    public static void main(String[] args) {

    }
}
