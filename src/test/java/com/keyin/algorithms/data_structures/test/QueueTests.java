package com.keyin.algorithms.data_structures.test;

import com.keyin.algorithms.data_structures.test.common.JavaCollectionTest;
import com.keyin.algorithms.data_structures.test.common.QueueTest;
import com.keyin.algorithms.data_structures.test.common.Utils;
import com.keyin.algorithms.data_structures.test.common.Utils.TestData;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class QueueTests {

    @Test
    public void testArrayQueue() {
        TestData data = Utils.generateTestData(2500);

        String aName = "Queue [array]";
        Queue.ArrayQueue<Integer> aQueue = new Queue.ArrayQueue<Integer>();
        Collection<Integer> aCollection = aQueue.toCollection();

        assertTrue(QueueTest.testQueue(aQueue, aName,
                                       data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(aCollection, Integer.class, aName,
                                                     data.unsorted, data.sorted, data.invalid));

        // Specific test based on bug
        aQueue = new Queue.ArrayQueue<Integer>();
        for (int i = 0; i < 1024; i++) {
            aQueue.offer(i);
        }
        aQueue.poll();
        aQueue.offer(1024);
        Iterator it = aQueue.toQueue().iterator();
        while (it.hasNext())
            it.next();
    }

    @Test
    public void testLinkedQueue() {
        TestData data = Utils.generateTestData(250);

        String lName = "Queue [linked]";
        Queue.LinkedQueue<Integer> lQueue = new Queue.LinkedQueue<Integer>();
        Collection<Integer> lCollection = lQueue.toCollection();

        Queue<Integer> queue = new LinkedList();
        queue.offer(1);
        queue.offer(2);
        Integer s = queue.poll();

        Queue<Integer> queue1 = new PriorityQueue<>();
        queue1.offer(1);
        queue1.offer(5);
        queue1.offer(8);
        queue1.add(4);
        queue1.remove();

        List<Integer> test = new ArrayList<>();


        assertTrue(QueueTest.testQueue(lQueue, lName,
                                       data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(lCollection, Integer.class, lName,
                                                     data.unsorted, data.sorted, data.invalid));

        lQueue = new Queue.LinkedQueue<Integer>();
        for (int i = 0; i < 1024; i++) {
            lQueue.offer(i);
        }
        lQueue.poll();
        lQueue.offer(1024);
        Iterator it = lQueue.toQueue().iterator();
        while (it.hasNext())
            it.next();
    }
}
