package com.keyin.algorithms.tree;


/**
 * Invert a binary tree.
 *
 * Example:
 *
 * Input:
 *
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 * Output:
 *
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 *
 * **/

public class InvertBinaryTree {

        Node root = Node.root;



        public static void invertBT(Node node) {
            if (node != null) {
                switchLR(node);
                invertBT(node.left);
                invertBT(node.right);
            }
        }

    private static void switchLR(Node node) {
            Node tmp  = node.right;
            node.right = node.left;
            node.left = tmp;
    }

    public static void main(String[] args) {
        invertBT(Node.root);
        int i=1;
    }


}
