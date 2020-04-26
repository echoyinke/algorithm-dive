package com.keyin.algorithms.linkedList;


/**
 *
 *
 反转一个单链表。

 示例:

 输入: 1->2->3->4->5->NULL
 输出: 5->4->3->2->1->NULL
 进阶:
 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？

 *
 * **/

public class ReverseLinkedList {

    // in-place solution, double pointer
    public static ListNode reverseLinkedList(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode tmp = curr.next;
            curr.next = pre;
            pre = curr;

            curr = tmp;
        }

        return pre;
    }

    // recursive solution
    public  static ListNode reverseList(ListNode head) {
        //递归终止条件是当前为空，或者下一个节点为空
        if(head==null || head.next==null) {
            return head;
        }
        //这里的cur就是最后一个节点
        ListNode cur = reverseList(head.next);
        //这里请配合动画演示理解
        //如果链表是 1->2->3->4->5，那么此时的cur就是5
        //而head是4，head的下一个是5，下下一个是空
        //所以head.next.next 就是5->4
        head.next.next = head;
        //防止链表循环，需要将head.next设置为空
        head.next = null;
        //每层递归函数都返回cur，也就是最后一个节点
        return cur;
    }

    // recursively
    static ListNode pre = null, tmp = null;
    public static ListNode reverseList1(ListNode head) {
        if (head == null)
            return pre;
        tmp = head.next;
        head.next = pre;
        pre = head;
        head = tmp;
        return reverseList1(head);
    }



    /**
     *反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
     *
     * 说明:
     * 1 ≤ m ≤ n ≤ 链表长度。
     *
     * 示例:
     *
     * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
     * 输出: 1->4->3->2->5->NULL
     *
     *
     * **/
    static int start = 2;
    static int end  = 4;
    static int index=1;
    public static ListNode reverseListInRange(ListNode head) {


        if (head == null || head.next == null || index<=start) {
            index++;
            return head;
        }
        reverseListInRange(head.next);
        if (index<=end && index>=start) {
            head.next.next = head;
            head.next = null;
        }
        return head;
    }

    private boolean stop;
    private ListNode left;

    public void recurseAndReverse(ListNode right, int m, int n) {

        // base case. Don't proceed any further
        if (n == 1) {
            return;
        }

        // Keep moving the right pointer one step forward until (n == 1)
        right = right.next;

        // Keep moving left pointer to the right until we reach the proper node
        // from where the reversal is to start.
        if (m > 1) {
            this.left = this.left.next;
        }

        // Recurse with m and n reduced.
        this.recurseAndReverse(right, m - 1, n - 1);

        // In case both the pointers cross each other or become equal, we
        // stop i.e. don't swap data any further. We are done reversing at this
        // point.
        if (this.left == right || right.next == this.left) {
            this.stop = true;
        }

        // Until the boolean stop is false, swap data between the two pointers
        if (!this.stop) {
            int t = this.left.val;
            this.left.val = right.val;
            right.val = t;

            // Move left one step to the right.
            // The right pointer moves one step back via backtracking.
            this.left = this.left.next;
        }
    }





    public static void main(String[] args) {
        ListNode listNode = ListNode.one2Five;
        ListNode listNode1 = reverseList1(listNode);

        ListNode listNode2 = reverseList(ListNode.arr2LinkedList(new int[]{1,2,3,4,5}));
        ListNode listNode3 = reverseListInRange(ListNode.arr2LinkedList(new int[]{1,2,3,4,5}));

        int i = 1;
    }

}
