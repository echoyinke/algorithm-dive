package com.keyin.algorithms.tree;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * return its bottom-up level order traversal as:
 * [
 *   [15,7],
 *   [9,20],
 *   [3]
 * ]
 *
 * **/

public class BottomUpLevelOrder {
    static List<List<Integer>> res = new ArrayList<>();
    static LinkedList<Node> linkedList = new LinkedList<>();



    static void bottomUpLevelOrder (Node node) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            List<Integer> tmp = new ArrayList<>();
            int levelBreadth = queue.size();
            while (levelBreadth>0) {
                Node  currNode = queue.poll();
                tmp.add(currNode.data);

                if (currNode.left != null) {
                    queue.add(currNode.left);
                }
                if (currNode.right != null) {
                    queue.add(currNode.right);
                }
                levelBreadth--;
            }
            res.add(new ArrayList<>(tmp));
            tmp.clear();
        }

    }


    public static void main(String[] args) {
        bottomUpLevelOrder(Node.root);
        int i = 1;
    }




}
