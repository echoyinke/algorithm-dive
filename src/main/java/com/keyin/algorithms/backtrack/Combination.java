package com.keyin.algorithms.backtrack;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 public void backtrack(int first, LinkedList<Integer> curr) {
 // if the combination is done
 if (curr.size() == k)
 output.add(new LinkedList(curr));

 for (int i = first; i < n + 1; ++i) {
 // add i into the current combination
 curr.add(i);
 // use next integers to complete the combination
 backtrack(i + 1, curr);
 // backtrack
 curr.removeLast();
 }
 }

 * **/
public class Combination {
    static int n =4;
    static int k=2;
    static int total;

    static List<List<Integer>> output = new ArrayList<>();

    public static void backtrack(int first, LinkedList<Integer> curr) {
        // if the combination is done
        if (curr.size() == k) {
            output.add(new LinkedList(curr));
            return;

        }

        for (int i = first; i < n + 1; ++i) {
            // add i into the current combination
                curr.add(i);
            // use next integers to complete the combination
            backtrack(i + 1, curr);
            // backtrack
            curr.removeLast();
        }
    }

    public static void main(String[] args) {
        backtrack(1, new LinkedList<>());
        int i = 1;
    }
}
