package com.keyin.algorithms.tree;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Given the below binary tree and sum = 22,
 *
 *       5
 *      / \
 *     4   8
 *    /   / \
 *   11  13  4
 *  /  \      \
 * 7    2      1
 *
 *
 */
public class PathSum {

    static List<List<Integer>> lists =new ArrayList<>();

    static boolean pathSum(Node node) {
        preOrder(node, new ArrayList<>());
        return true;

    }

    static void preOrder(Node node, List<Integer> list) {
        if (node == null) {
            return;
        }
        list.add(node.data);
        if (node.right == null && node.left == null) {
            lists.add(list);
        }
        if (node.left != null) {
            preOrder(node.left,new ArrayList<>(list));
        }
        if (node.right != null) {
            preOrder(node.right, new ArrayList<>(list));
        }
    }

    public static void main(String[] args) {
        pathSum(Node.root);
        int i=1;
    }


}
