package com.keyin.javalang.syncronize.syncronized;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * testUnfairEnoughOfEnqueue:默认策略下，在A释放锁后一定是C线程先获得锁。因为在获取锁时，是将当前线程插入到cxq的头部，而释放锁时，默认策略是：如果EntryList为空，
 * 则将cxq中的元素按原有顺序插入到到EntryList，并唤醒第一个线程。也就是当EntryList为空时，是后来的线程先获取锁。这点JDK中的Lock机制是不一样的。
 * cxq（后来先运行，后来到线程插到队首）
 * 
 * testUnfairWhileWaitNotify:将以上代码反复执行多次，结果都是B释放锁之后A会先得到锁，这又是为什么呢？C为何不能先拿到锁呢？
 * 原因：ObjectWaiter对象存在于WaitSet、EntryList、cxq（后来先运行，后来到线程插到队首）等集合中，或者正在这些集合中移动
 *    ： 如果WaitSet中有，先处理waitset， 
 * */
public class FatLockUnfairOrFair {
    private static Logger log = LoggerFactory.getLogger(FatLockUnfairOrFair.class);
    
    private static Thread  thread1, thread2, thread3;
    private static Lock lockee =new Lock();
    
    final Object lock = new Object();
    
    public static void testUnfairEnoughOfEnqueue() throws InterruptedException {
        FatLockUnfairOrFair fatLockUnfairOrFair1 = new FatLockUnfairOrFair();
        fatLockUnfairOrFair1.startThreadA();
        Thread.sleep(100);
        fatLockUnfairOrFair1.startThreadB();
        Thread.sleep(100);
        fatLockUnfairOrFair1.startThreadC();
    }
    
    public static void testfairWhileWaitNotify() {
        FatLockUnfairOrFair fatLockUnfairOrFair2 = new FatLockUnfairOrFair();
        fatLockUnfairOrFair2.startThread1();

    }
    public static void testUnfairWhileWaitNotify() throws InterruptedException {
        thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lockee) {
                    log.info("Thread  1 acquired the lock. and wait it");
                    lockee.wait();
                    log.info("Thread 1 acquired the lock again.");
                }
            }
        });
        thread2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lockee) {
                    log.info("Thread  2 acquired the lock. and wait it");
                    lockee.wait();
                    log.info("Thread 2 acquired the lock again.");
                }
            }
        });
        thread3 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lockee) {
                    log.info("Thread  3 acquired the lock. and notify it");
                    lockee.notify();
                    log.info("Thread 3 run again.");
                }
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(300);
        thread3.start();
        

    }
    

    public static void main(String[] args) throws InterruptedException {
//        testUnfairWhileWaitNotify();
        testUnfairWhileWaitNotify();
    }
    

    public void startThreadA() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("A get lock");
                    Thread.sleep(500);
                    System.out.println("A release lock");
                } 
            }
        }, "thread-A").start();
    }

    public void startThreadB() {
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("B get lock");
            }
        }, "thread-B").start();
    }

    public void startThreadC() {
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("C get lock");
            }
        }, "thread-C").start();
    }

    private static void sleep(long sleepVal){
        try{
            Thread.sleep(sleepVal);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void log(String desc){
        System.out.println(Thread.currentThread().getName() + " : " + desc);
    }
    
    public  void startThread1(){
        new Thread(() -> {
            synchronized (lock){
                log("get lock");
                startThread2();
                log("start wait");
                try {
                    lock.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                log("get lock after wait");
                log("release lock");
            }
        }, "thread-1").start();
    }

    public void startThread2(){
        new Thread(()->{
            synchronized (lock){
                log("get lock");
                startThread3();
                sleep(100);
                log("start notify");
                lock.notify();
                log("release lock");

            }
        },"thread-2").start();
    }

    public void startThread3(){
        new Thread(() -> {
            synchronized (lock){
                log("get lock");
                log("release lock");
            }
        }, "thread-3").start();
    }



}