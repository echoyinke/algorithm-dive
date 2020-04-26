package com.keyin.algorithms.linkedList;


import com.keyin.algorithms.data_structures.List;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    static ListNode one2Five;
    static {
        one2Five = arr2LinkedList(new int[] {1,2,3,4,5});

    }

    public static ListNode arr2LinkedList(int[] arr) {
        ListNode head  = new ListNode(arr[0]);
        ListNode tmp =head;
        for (int i = 1; i < arr.length; i++) {
            ListNode currNode  = new ListNode(arr[i]);
            tmp.next = currNode;
            tmp = currNode;

        }
        return head;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1,2,3,4,5};
        ListNode listNode = arr2LinkedList(arr);
        int i =1 ;
    }

}
