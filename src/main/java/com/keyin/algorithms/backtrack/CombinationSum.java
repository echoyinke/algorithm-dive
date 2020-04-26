package com.keyin.algorithms.backtrack;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target),
 * find all unique combinations in candidates where the candidate numbers sums to target.
 *
 * The same repeated number may be chosen from candidates unlimited number of times.
 *
 * Note:
 *
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * Example 1:
 *
 * Input: candidates = [2,3,6,7], target = 7,
 * A solution set is:
 * [
 *   [7],
 *   [2,2,3]
 * ]
 * Example 2:
 *
 * Input: candidates = [2,3,5], target = 8,
 * A solution set is:
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 * **/
public class CombinationSum {

    static int target = 8;
    static List<Integer> candidates = Arrays.asList(2,3,6,7);

    static List<List<Integer>> res = new ArrayList<>();
    public static final void backtrack(List<Integer> curr) {

        if (curr.stream().mapToInt(Integer::intValue).sum()>target) {
            return;
        }

        if (curr.stream().mapToInt(Integer::intValue).sum()==target) {
            res.add(new ArrayList<>(curr));
            return;
        }

        for (int i = 0; i< candidates.size() ;i++ ){
            curr.add(candidates.get(i));
            backtrack(curr);
            curr.remove(candidates.get(i));
        }


    }


    public static void main(String[] args) {
        backtrack(new ArrayList<>());
        int i =1;

    }

}
