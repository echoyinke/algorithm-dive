package com.keyin.algorithms.priorityqueue;


import java.util.*;

/**
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 * 示例 1:
 *
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2:
 *
 * 输入: nums = [1], k = 1
 * 输出: [1]

 * **/
public class TopKFrequency {


    public static int[] res;

    public static List<Integer> findTopFreq(int[] arr, int k) {
         HashMap<Integer, Integer> freq = new HashMap<>();
         PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(freq::get));

        for (int i = 0; i < arr.length; i++) {
            freq.put(arr[i], freq.getOrDefault(arr[i], 0)+1);
        }

        for (int n: freq.keySet()) {
            priorityQueue.add(n);
            if (priorityQueue.size() > k)
                priorityQueue.poll();
        }
        List<Integer> top_k = new LinkedList();
        while (!priorityQueue.isEmpty())
            top_k.add(priorityQueue.poll());
        Collections.reverse(top_k);
        return top_k;



    }


    public static void main(String[] args) {
        List<Integer> s = findTopFreq(new int[]{1,1,1,2,2,3}, 2);
        int i =1 ;

    }


}
