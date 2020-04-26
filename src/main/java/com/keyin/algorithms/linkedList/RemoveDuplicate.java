package com.keyin.algorithms.linkedList;



/**
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 *
 * Example 1:
 *
 * Input: 1->1->2
 * Output: 1->2
 * Example 2:
 *
 * Input: 1->1->2->3->3
 * Output: 1->2->3
 *
 * **/
public class RemoveDuplicate {

    public static void rmDup(ListNode listNode) {


        while (listNode != null) {
            if (listNode.next !=null &&listNode.val == listNode.next.val) {
                listNode.next = listNode.next.next;
            }
            listNode = listNode.next;
        }


    }



    public static void main(String[] args) {
        ListNode listNode = ListNode.arr2LinkedList(new int[]{1,1,2,3,3}) ;
        rmDup(listNode);
        int i =1 ;
    }
}
