package com.keyin.algorithms.linkedList;

/**
 *
 * 编写一个程序，找到两个单链表相交的起始节点。
 *
 * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
 * 输出：Reference of the node with value = 8
 * 输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
 *
 * **/



public class ListIntersection {


    // 遍历一下 A+B， 和 B+A, 步数是一样的，如果最后是重合的，最后几步势必是相同的节点
    public static final Integer findIntersection(ListNode listNode, ListNode listNode2) {
        ListNode p1 = listNode, p2= listNode2;
        boolean append1 = true, append2 = true, res = false;

       while (p1 != null || p2 != null) {


           if (p1 == p2) {
               return p1.val;
           }
           if(p1.next ==null && append1) {
               p1 = listNode2;
               append1 = false;
           } else {
               p1 = p1.next;
           }
           if(p2.next ==null && append2) {
               p2 = listNode;
               append2 = false;
           } else {
               p2 = p2.next;

           }


       }
       return null;

    }

    public static void main(String[] args) {
        int[] test = new int[]{1,2,3,4};
        ListNode listNode = ListNode.arr2LinkedList(new int[]{1,2,3,4,5,6,7});
        ListNode listNode1 = ListNode.arr2LinkedList(new int[]{8,9});
        listNode1.next.next = listNode.next.next.next.next.next;
        int res = findIntersection(listNode, listNode1);
        int i =1;

    }

}
