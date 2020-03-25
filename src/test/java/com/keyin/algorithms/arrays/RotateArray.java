package com.keyin.algorithms.arrays;

public class RotateArray {
    public static void rotate(int[] nums, int k) {
        k = k % nums.length;
        if(k > 0){
//            swap(nums, 0, nums.length-k-1);
//            swap(nums, nums.length-k, nums.length-1);
            swap(nums, 0, nums.length-1);
        }
    }

    public static void swap(int[] arr, int start, int end){
        while(start < end){
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;

            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        int[] input = new int[]{1,2,3,4,5,6,7};
        RotateArray.rotate(input.clone(), 3);
    }



}
