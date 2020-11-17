package com.keyin.algorithms.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @description: sdf
 * @author: yinke
 * @create: 2020-10-19 13:03
 **/
public class BlockingQueueTest {
    public static void main(String[] args) {
        //Creating BlockingQueue of size 10
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        //starting producer to produce messages in queue
        new Thread(producer).start();
        //starting consumer to consume messages from queue
//        new Thread(consumer).start();
        System.out.println("Producer and Consumer has been started");
    }
    

}
