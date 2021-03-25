package com.keyin.multithread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: yinke
 * @create: 2021-03-22 23:19
 **/
public class Foo {
    private AtomicInteger firstJobDone = new AtomicInteger(0);
    private AtomicInteger secondJobDone = new AtomicInteger(0);

   
    public void first() {
        System.out.println("first");
        firstJobDone.incrementAndGet();
    }

    public void second() {
        while (firstJobDone.get() != 1) {
            // waiting for the first job to be done.
        }
        
        System.out.println("second");
        secondJobDone.incrementAndGet();
    }

    public void third() {
        while (secondJobDone.get() != 1) {
            // waiting for the second job to be done.
        }
        
        System.out.println("third");
    }
    public static void main(String[] args) {
        Foo foo = new Foo();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                foo.first();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                foo.second();
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                foo.third();
            }
        });
        
        thread2.start();
        thread1.start();
        thread3.start();
        
    }
}

   
