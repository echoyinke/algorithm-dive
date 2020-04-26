package com.keyin.algorithms.array;

public class findDuplicatesRightIndex {

    public static int findDuplicatesRightIndex(int[] nums) {
        if (nums.length < 2) return -1;
        int i = nums.length - 2;
        for (int j = nums.length-1; j>=0; j--) {
            if (nums[j] != nums[i]) {
                i--;
            }else {
                return j;
            }
        }
        return -1;
    }



    public static void main(String[] args) {
        int[] arr = new int[]{1,1,2};
        int res = findDuplicatesRightIndex(arr);
        int i =1 ;

    }

}
