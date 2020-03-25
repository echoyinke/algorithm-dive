package com.keyin.algorithms.tree;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Node {
    int data;
    Node right;
    Node left;


    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
