package com.keyin.algorithms.priorityqueue;

import java.util.PriorityQueue;

public class TheKthLargeNum {
    public static int findKthLargest(int[] nums, int k) {
        // init heap 'the smallest element first'
        PriorityQueue<Integer> heap =
                new PriorityQueue<>();

        // keep k largest elements in the heap
        for (int n: nums) {
            heap.add(n);
            if (heap.size() > k)
                heap.poll();
        }

        // output
        return heap.poll();
    }


    public static void main(String[] args) {
        int res = findKthLargest(new int[]{9,3,5,7,2}, 3);
        System.out.println(res);
    }

}
