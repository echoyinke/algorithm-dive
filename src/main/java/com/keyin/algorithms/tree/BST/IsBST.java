package com.keyin.algorithms.tree.BST;


import com.keyin.algorithms.tree.Node;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BISchemaBinding;
import com.sun.xml.internal.bind.marshaller.NoEscapeHandler;

import java.util.Stack;

/**
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 *
 * Example 1:
 *
 *     2
 *    / \
 *   1   3
 *
 * Input: [2,1,3]
 * Output: true
 * Example 2:
 *
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 *
 * Input: [5,1,4,null,null,3,6]
 * Output: false
 * Explanation: The root node's value is 5 but its right child's value is 4.
 *
 * **/
public class IsBST {


    static boolean isBST(Node node, Integer min, Integer max) {
        if (node == null) {
           return true;
        }
        boolean nodeOK =    node.data <= max && node.data>=min;

        boolean leftOk = isBST(node.left, min, node.data);
        boolean rightOk =  isBST(node.right, node.data, max);
        return nodeOK && leftOk && rightOk;
     }

     //A BST's pre order is a ascend sequence of num. (left, root, right)
    static int tmp = Integer.MIN_VALUE;
     static boolean isPreOrderIsBST(Node node) {
         Stack<Node> stack = new Stack<>();
         while (!stack.empty() && node!=null) {
             while (node!=null) {
                 stack.push(node);
                 node = node.left;
             }
             Node currNode = stack.pop();
             if (currNode.data <= tmp) {
                 return false;
             }
             tmp = currNode.data;
             node = currNode.right;

         }
          return true;
     }

    public static void main(String[] args) {
        boolean res = isBST(Node.root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        Node trueBST = new Node(20);
        trueBST.right = new Node(27);
        trueBST.left = new Node(18);
        trueBST.left.left = new Node(15);
        trueBST.left.right = new Node(19);
        trueBST.right.left = new Node(24);
        trueBST.right.right = new Node(29);
        boolean res2 = isBST(trueBST, Integer.MIN_VALUE, Integer.MAX_VALUE);
        boolean res3 = isPreOrderIsBST(trueBST);

        int i = 1;
    }
}
