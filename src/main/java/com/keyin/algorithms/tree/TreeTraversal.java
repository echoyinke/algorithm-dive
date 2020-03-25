package com.keyin.algorithms.tree;


import org.junit.Assert;

import java.util.*;

/**
 *       1
 *      / \
 *     2   3
 *    / \   \
 *   4  5    6
 *     /\
 *    7  8
 * <p>
 * 前序遍历(DFS)：1  2  4  5  7  8  3  6
 * <p>
 * 中序遍历(DFS)：4  2  7  5  8  1  3  6
 * <p>
 * 后序遍历(DFS)：4  7  8  5  2  6  3  1
 * <p>
 * 层次遍历(BFS)：1  2  3  4  5  6  7  8
 **/
public class TreeTraversal {
    static Node root;
    static List<Integer> preOrderList = Arrays.asList(1, 2, 4, 5, 7, 8, 3, 6);
    static List<Integer> inOrderList = Arrays.asList(4, 2, 7, 5, 8, 1, 3, 6);
    static List<Integer> postOrderList = Arrays.asList(4, 7, 8, 5, 2, 6, 3, 1);
    static List<Integer> levelOrderList = Arrays.asList(1 , 2 , 3 , 4  ,5 , 6 , 7 , 8);
    static List<Integer> res = new ArrayList<>();


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

    void levelOrderTraversal(Node node) {
        if (node == null) {
            return;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node curNode = queue.poll();
            res.add(curNode.data);
            if (curNode.left != null) {
                queue.add(curNode.left);

            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }

        }

    }


    void preOrderTraversal(Node node) {
        if (node == null) {
            return;
        }
        res.add(node.data);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }
    public void preOrderNonRecur(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.empty()){
            Node curNode = stack.pop();
            res.add(curNode.data);
            if (curNode.right != null) {
                stack.push(curNode.right);
            }
            if (curNode.left != null) {
                stack.push(curNode.left);
            }
        }


    }

    void inOrderTraversal(Node node) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left);
        res.add(node.data);
        inOrderTraversal(node.right);

    }
    public void inOrderNonRecur(Node node) {

        if (node == null) {
            return;
        }
        Node curr = node;
        Stack<Node> stack = new Stack<>();
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.data);
            curr = curr.right;
        }
    }

    void postOrderTraversal(Node node) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        res.add(node.data);
    }



    public void postOrderNonRecur(Node node) {
        if(node == null)
            return;
        Stack<Node> s = new Stack<Node>();

        Node curNode; //当前访问的结点
        Node lastVisitNode; //上次访问的结点
        curNode = node;
        lastVisitNode = null;

        //把currentNode移到左子树的最下边
        while(curNode != null){
            s.push(curNode);
            curNode = curNode.left;
        }
        while(!s.empty()){
            curNode = s.pop();  //弹出栈顶元素
            //一个根节点被访问的前提是：无右子树或右子树已被访问过
            if(curNode.right!= null && curNode.right !=lastVisitNode){
                //根节点再次入栈
                s.push(curNode);
                //进入右子树，且可肯定右子树一定不为空
                curNode = curNode.right;
                while(curNode!= null){
                    //再走到右子树的最左边
                    s.push(curNode);
                    curNode = curNode.left;
                }
            }else{
                //访问
                res.add(curNode.data);
                //修改最近被访问的节点
                lastVisitNode = curNode;
            }
        }


    }

    public void posOrder(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> s1 = new Stack<Node>();
        Stack<Node> s2 = new Stack<Node>();
        s1.push(head);
        while (!s1.isEmpty()) {
            Node cur = s1.pop();
            s2.push(cur);
            if (cur.left != null) {
                s1.push(cur.left);
            }
            if (cur.right != null) {
                s1.push(cur.right);
            }
        }
        while (!s2.isEmpty()) {
            Node cur = s2.pop();
            System.out.println(cur.data);
        }
    }

    public static void main(String[] args) {
        TreeTraversal traversal = new TreeTraversal();
        traversal.inOrderTraversal(root);
        Assert.assertArrayEquals(res.toArray(), inOrderList.toArray());
        res.clear();
        traversal.preOrderNonRecur(root);
        Assert.assertArrayEquals(res.toArray(), preOrderList.toArray());
        res.clear();
        traversal.inOrderNonRecur(root);
        res.clear();
        traversal.postOrderNonRecur(root);
        Assert.assertArrayEquals(res.toArray(), postOrderList.toArray());
        res.clear();
        traversal.posOrder(root);
        Assert.assertArrayEquals(res.toArray(), postOrderList.toArray());
        res.clear();
        traversal.levelOrderTraversal(root);
        Assert.assertArrayEquals(res.toArray(), levelOrderList.toArray());
        res.clear();

    }


}
