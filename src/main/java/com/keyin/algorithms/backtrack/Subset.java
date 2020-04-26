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


    List<List<Integer>> lists = new ArrayList<>();
    public List<List<Integer>> subsets1(int[] nums) {
        if(nums == null || nums.length ==0){
            return lists;
        }

        List<Integer> list = new ArrayList<>();

        process(list, nums, 0);
        return lists;

    }

    private void process(List<Integer>list, int[] nums, int start){

        lists.add(new ArrayList<>(list));
        // 从第一个数判断到最后一个数。
        for(int i = start; i < nums.length; i++){

            // 取出来一个，放入临时结果。
            list.add(nums[i]);
            // i 为 取的情况。
            process(list, nums, i+1);
            // i 为不取的情况
            list.remove(list.size()-1);
        }
    }

    public static void preOrderProc(int[] nums, int i, ArrayList<Integer> subset, List<List<Integer>> res) {
        if (i >= nums.length) {
           return;
        }
        res.add(new ArrayList<>(subset));
        preOrderProc(nums, i+1, subset, res);
        subset.add(nums[i]);
        preOrderProc(nums, i+1, subset, res);
    }


    /**
     * DFS，中序遍历
     */
    public  void inOrder(int[] nums, int i, ArrayList<Integer> subset, List<List<Integer>> res) {
        if (i >= nums.length) return;
        subset = new ArrayList<Integer>(subset);

        inOrder(nums, i + 1, subset, res);
        subset.add(nums[i]);


        // 因为 subset 是引用，所以加值之后就变右子树。
        res.add(subset);
        inOrder(nums, i + 1, subset, res);
    }

    public List<List<Integer>> binaryBit(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i = 0; i < (1 << nums.length); i++) {
            List<Integer> sub = new ArrayList<Integer>();
            for (int j = 0; j < nums.length; j++) {
                int iRMj= i>>j;
                int resultAnd1 = iRMj&1;
                if (resultAnd1 == 1) {
                    sub.add(nums[j]);
                }

            }
            res.add(sub);
        }
        return res;
    }





    public static void main(String[] args) {
       Subset subset = new Subset();
       List<List<Integer>> res = subset.subsets1(new int[]{1, 2, 3});
        List<List<Integer>> res2 = new ArrayList<>();
       subset.inOrder(new int[]{1,2}, 0, new ArrayList<Integer>(), res2);
        List<List<Integer>> res3 = subset.binaryBit(new int[]{1,2,3});

       int i = 1;


    }





}
