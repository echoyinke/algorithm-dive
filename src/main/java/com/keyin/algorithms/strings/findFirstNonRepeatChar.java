package com.keyin.algorithms.strings;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
 *
 * 案例:
 *
 * s = "leetcode"
 * 返回 0.
 *
 * s = "loveleetcode",
 * 返回 2.
 *
 *
 * **/

public class findFirstNonRepeatChar {



    public static boolean isPowLoop(int target) {
        if(target == 1) {return true;}
        Set<Integer> set = new HashSet<>();
        set.add(target);
        while(true){
            int sum = powSum(target);

            if(sum == 1) {
                return true;
            };
            if(set.contains(sum)) {
                return false;
            };
            set.add(sum);
            target = sum;

        }

    }

    private static int powSum(int num) {
        int sum =0;
        int tmp = 0;
        while(num != 0) {
            tmp = num%10;
            sum += tmp*tmp;
            num = (num/10);
        }
        return sum;

    }


    public static void main(String[] args) {
      boolean res = isPowLoop(11);
      int i = 1;
    }
}
