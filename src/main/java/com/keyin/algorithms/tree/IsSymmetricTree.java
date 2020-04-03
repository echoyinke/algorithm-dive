package com.keyin.algorithms.tree;

/**
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *
 *
 * **/


public class IsSymmetricTree {

    public static boolean isSymmetric(Node left, Node right) {
        if(left == null && right == null) return true;
        if(left == null || right == null) return false;

        boolean isDataEqual = left.data == right.data;
       return isDataEqual && isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);

    }


    public static void main(String[] args) {
        isSymmetric(Node.root, Node.root);
        Node symTree = new Node(1);
        symTree.left = symTree.right = new Node(2);
        symTree.left.left = symTree.right.right = new Node(3);
        symTree.left.right = symTree.right.left = new Node(4);
        isSymmetric(symTree, symTree);

    }


}
