package com.keyin.algorithms.backtrack;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 *输入: nums = [1,2,3]
 * 输出:
 * [
 *   [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 *
 */
public class Subset {

    public synchronized void test() throws InterruptedException {
        while (true) {
            System.out.println(Thread.currentThread().getName());
        }
    }

    List<List<Integer>> lists = new ArrayList<>();
    public List<List<Integer>> subsets1(int[] nums) throws NoSuchMethodException {
        if(nums == null || nums.length ==0){
            return lists;
        }

        List<Integer> list = new ArrayList<>();

        process(list, nums, 0);
        return lists;

    }

    private void process(List<Integer>list, int[] nums, int start){

        lists.add(list);
        for(int i = start; i < nums.length; i++){

            list.add(nums[i]);
            process(list, nums, i+1);
            list.remove(list.size()-1);
        }
    }



    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            Subset s = new Subset();
            try {
                s.test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        Thread t2 = new Thread(() -> {
            Subset s = new Subset();
            try {
                s.test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        int[] arr = new int[] {1,2,3};
        Subset subset = new Subset();
        String a = "abc";
        String b = "abc";
        String c = new String("abc");
        List<String> s = new ArrayList<>();
        List<Integer> in = new ArrayList<>();
        int i=1;


    }





}
