package com.keyin.algorithms.linkedList;



/**
 *
 *
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。

 示例：

 给定一个链表: 1->2->3->4->5, 和 n = 2.

 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 **/
public class RemoveNthNodeFromEnd {


    static int num = 1;
    static int target = 4;
    public static void rmNthFromEnd(ListNode node) {
        if (node == null || node.next ==null) {
            return;
        }

        rmNthFromEnd(node.next);
        num++;
        if(num == (target + 1)) {
            if (node.next.next != null) {
                node.next = node.next.next;
            }
            else {
                node.next = null;
            }
        }


    }

    public static void main(String[] args) {
        ListNode listNode = ListNode.one2Five;
        rmNthFromEnd(listNode);
        int i = 1;
    }
}
