package com.keyin.algorithms.tree;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Node {
    public Integer data;

    public Node right;
    public Node left;


    public Node(Integer data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public static Node root;

    static {
        root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.left.right.left = new Node(7);
        root.left.right.right = new Node(8);
        root.right.right = new Node(6);
    }

    @Override
    public String toString() {
        if (null == this) {
            return "null";
        }
        String leftPart = (null == left ? "null" : String.valueOf(left.data));
        String rightPart = (null == right ? "null" : String.valueOf(right.data));

        return "Node{" +
                "data=" + data +
                ", left=" + leftPart +
                ", right=" + rightPart +
                "}";
    }
}
