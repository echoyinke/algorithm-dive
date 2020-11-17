package com.keyin.algorithms.queue;

import java.util.concurrent.BlockingQueue;

/**
 * @description:
 * @author: yinke
 * @create: 2020-10-19 13:10
 **/
public class Producer implements Runnable{

    private BlockingQueue<Message> queue;

    public Producer(BlockingQueue<Message> q) {
        this.queue = q;
    }

    @Override
    public void run() {
        //produce messages
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("" + i);
            try {
                queue.put(msg);
                System.out.println("Produced " + msg.getMsg());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch (Exception e) {
                System.out.println(e);
            }
        }
        //adding exit message
        Message msg = new Message("exit");
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
