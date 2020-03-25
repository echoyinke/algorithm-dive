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
