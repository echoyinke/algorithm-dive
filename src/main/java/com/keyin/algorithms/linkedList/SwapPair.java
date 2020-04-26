package com.keyin.algorithms.linkedList;

public class SwapPair {

    public static ListNode swapPair(ListNode head) {
        // Dummy node acts as the lastPairTail for the head node
        // of the list and hence stores pointer to the head node.
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode lastPairTail = dummy;
        ListNode curr = head;
        ListNode ret = head.next;
        while (curr !=null && curr.next != null) {
            // Nodes to be swapped
            ListNode firstNode = curr;
            ListNode secondNode = curr.next;

            // Swapping
            curr = secondNode.next;
            lastPairTail.next = secondNode;
            secondNode.next = firstNode;
            lastPairTail = firstNode;

        }
        return ret;
    }


    public static void main(String[] args) {
       ListNode listNode = ListNode.arr2LinkedList(new int[]{1,2,3,4});
       ListNode res = swapPair(listNode);
       int i = 1;
    }
}
