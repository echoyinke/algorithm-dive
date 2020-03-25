package com.keyin.algorithms.tree;



/**
 *     1
 *    / \
 *   2   3
 *  / \   \
 * 4  5    6
 *    /\
 *   7  8
 *
 * 前序遍历：1  2  4  5  7  8  3  6
 *
 * 中序遍历：4  2  7  5  8  1  3  6
 *
 * 后序遍历：4  7  8  5  2  6  3  1
 *
 * 层次遍历：1  2  3  4  5  6  7  8
 *
 * **/
public class TreeTraversal {
    static Node root;

    static {
        root = new Node(1);
        root.left = new Node(2);
        root.right=new Node(3);
        root.left.left= new Node(4);
        root.left.right = new Node(5);
        root.left.right.left= new Node(7);
        root.left.right.right = new Node(8);
        root.right.right = new Node(6);
    }

    void inOrderTraversal(Node node) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left);
        inOrderTraversal(node.right);

    }


    void preOrderTraversal(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.data);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    void postOrderTraversal(Node node) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node);
        postOrderTraversal(node);
        System.out.println(node.data);
    }

    public static void main(String[] args) {
        TreeTraversal traversal = new TreeTraversal();
        traversal.inOrderTraversal(root);
    }


}
