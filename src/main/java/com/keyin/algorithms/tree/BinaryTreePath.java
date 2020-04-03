package com.keyin.algorithms.tree;


import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree, return all root-to-leaf paths.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Input:
 *
 *    1
 *  /   \
 * 2     3
 *  \
 *   5
 *
 * Output: ["1->2->5", "1->3"]
 *
 * Explanation: All root-to-leaf paths are: 1->2->5, 1->3
 *
 * **/
public class BinaryTreePath {

    static List<String> res = new ArrayList<>();

    public static List<String> binaryTreePath(Node node) {
        StringBuilder stringBuilder = new StringBuilder();
        preOrder(node, stringBuilder);
        return res;
    }

    public static void preOrder(Node node, StringBuilder stringBuilder) {

        if (node != null) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append("->");
            }
            stringBuilder.append(node.data);
        }
        if (node.left == null && node.right == null) {
            res.add(stringBuilder.toString());
        }
        if (node.left != null) {
            preOrder(node.left, new StringBuilder(stringBuilder));
        }
        if (node.right != null) {
            preOrder(node.right, new StringBuilder(stringBuilder));
        }

    }

    public static void main(String[] args) {
       List<String> res =  BinaryTreePath.binaryTreePath(Node.root);
       int i=1;
    }




}
