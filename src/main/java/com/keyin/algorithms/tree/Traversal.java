package com.keyin.algorithms.tree;

public class Traversal {
    Node root;

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


}
