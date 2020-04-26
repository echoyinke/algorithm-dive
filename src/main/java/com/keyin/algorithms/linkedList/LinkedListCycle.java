package com.keyin.algorithms.linkedList;

import java.util.HashSet;

public class LinkedListCycle {

    public static boolean isLinkedListCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        HashSet<Integer> set = new HashSet();
        ListNode node = head;
        while (node != null) {
            if (set.contains(node.val)) {
                return true;
            }
            set.add(node.val);
            node = node.next;
        }

        return false;

    }



    public static void main(String[] args) {
        ListNode node = ListNode.one2Five;
        ListNode node2 = node.next;
        ListNode node5 = node2.next.next.next;
        node5.next = node2;
        boolean res = isLinkedListCycle(ListNode.one2Five);
        boolean res2 = isLinkedListCycle(node);

        int i = 1 ;
    }

}
