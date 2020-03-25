package com.keyin.algorithms.arrays;

public class FindFirstMissingPositiveInteger {
        public static int firstMissingPositive(int[] nums) {
            int i = 0;
            while (i < nums.length) {
                int j = nums[i];
                if (j >= 0 && j < nums.length && j != i && nums[j] != j) {
                  swap(nums, i, j);
                }
                else i ++;
            }

            for (i = 1; i < nums.length; i ++) {
                if (i != nums[i])
                    return i;
            }

            return nums.length > 0 && i == nums[0] ? i + 1 : i;
        }
        public static void swap(int[] nums, int i , int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }

    public static void main(String[] args) {
        int[] test = new int[]{2,3,9,1,99,5,4,22,18,39};
        int result = FindFirstMissingPositiveInteger.firstMissingPositive(test);
        System.out.println(result);
    }
}
