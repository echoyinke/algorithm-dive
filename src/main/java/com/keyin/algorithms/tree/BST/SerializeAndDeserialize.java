package com.keyin.algorithms.tree.BST;

import com.keyin.algorithms.tree.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SerializeAndDeserialize {


    static StringBuilder stringBuilder = new StringBuilder();

    static void serializeTreeNode(Node node, StringBuilder stringBuilder) {
        if (node == null) {
            stringBuilder.append("null, ");
        }
        stringBuilder.append(node.data.toString() + ", ");
        serializeTreeNode(node.left, stringBuilder);
        serializeTreeNode(node.right, stringBuilder);


    }

    public static void  deserializeTree(String str) {

    }




    public static void main(String[] args) {
        serializeTreeNode(Node.root, stringBuilder);
        int i=1;

    }

}
