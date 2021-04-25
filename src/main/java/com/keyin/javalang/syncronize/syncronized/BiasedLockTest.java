package com.keyin.javalang.syncronize.syncronized;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * @description: 
 * 
 * 
 *    运行参数：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0 -XX:+PrintCommandLineFlags
 *    
 *   
 * 
 * 
 * @see "https://wiki.openjdk.java.net/display/HotSpot/Synchronization"
 * 如果有hashcode：|  hash code  | age | 0 | 01 |
 * 没有hashcode 可以偏向：|   0（thread ID）   |epoch| age | 0 | 01 |
 * object header markword：hashcode,
 * @author: yinke 无锁001， 偏向锁：101(第一个1代表是否可以偏向， 1-biasable， 0-nonbiasable)（末尾是5） （54位线程指针）， 轻量级锁00，重量级锁10， GC标记：11
 * @create: 2021-04-20 17:35
 **/
public class BiasedLockTest {
    private static Logger log = LoggerFactory.getLogger(BulkRebiaseOrBulkRevoke.class);


    public static class PeekObjHeader {
    }
    

    /*单个线程，可重入，比较线程ID是不是自己*/
    public static void testReentryBiasedLock() throws InterruptedException {
        PeekObjHeader peekObjHeaderAfterJvmStart = new PeekObjHeader();
        System.out.println("before lock");
        System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
        

        Thread thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("thread 1 acquire lock.");
                synchronized (peekObjHeaderAfterJvmStart) {
                    System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
                }
                synchronized (peekObjHeaderAfterJvmStart) {
                    System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
                }
            }
        });

      
        thread1.start();
        thread1.join();

    }
    
    
    /*如果是匿名偏向，可以获得偏向锁。如果实名偏向，则CAS失败，即使没有竞争*/
    public static void testCASBiasedLockWithoutRace() throws InterruptedException {
        PeekObjHeader peekObjHeaderAfterJvmStart = new PeekObjHeader();
        System.out.println("before lock");
        System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());

//        /* 如果我们给对象写了hashcode，那么将写不开偏向锁，会直接分配 thin lock*/
//        System.out.println("if we write hashcode " +Integer.toHexString(System.identityHashCode(peekObjHeaderAfterJvmStart)) + " to object header, we loose biased lock");
//        System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());

        Thread thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("thread 1 acquire lock.");
                synchronized (peekObjHeaderAfterJvmStart) {
                    System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
                }
            }
        });
        
        Thread thread2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("thread 2 acquire lock. after thread 1 release lock");
                synchronized (peekObjHeaderAfterJvmStart) {
                    System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
                }
            }
        });
        thread1.start();
        Thread.sleep(5000);
        /*这里t2获取偏向锁失败，升级thin lock*/
        thread2.start();
        Thread.sleep(2000);
        System.out.println("after thread 2 release lock.");
        System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
    }
    
    public static void testRevokeBiasedLock() throws InterruptedException {
        PeekObjHeader peekObjHeaderAfterJvmStart = new PeekObjHeader();
        System.out.println("before lock");
        System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
        
        Thread thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("thread 1 acquire lock.");
                synchronized (peekObjHeaderAfterJvmStart) {
                    System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
                }
                LockSupport.park();
                System.out.println("after thread2 acquire lock. the lock should be revoked,now thread try again");
                System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("thread 2 acquire lock.");
                synchronized (peekObjHeaderAfterJvmStart) {
                    System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
                }
              LockSupport.unpark(thread1);
            }
        });
        thread1.start();
        /*睡一秒,再启动thread2*/
        Thread.sleep(1000);
        thread2.start();
    } 
    
    public static void testRevokeBiasedLockWhichStillInSync() throws InterruptedException {
        PeekObjHeader peekObjHeaderAfterJvmStart = new PeekObjHeader();
        System.out.println("before lock");
        System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());


        Thread thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("thread 1 acquire lock.");
                synchronized (peekObjHeaderAfterJvmStart) {
                    System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
                    for (int i = 0; i < 99999; i++) {
                        
                    }
                    System.out.println("after thread2 acquire lock. the lock should be revoked,now thread try again");
                    System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
                }
               
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("thread 2 acquire lock.");
                synchronized (peekObjHeaderAfterJvmStart) {
                    System.out.println(ClassLayout.parseInstance(peekObjHeaderAfterJvmStart).toPrintable());
                }
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();

    }


    public static void main(String[] args) throws InterruptedException {
        // jvm启动时候存在大量race 所以前4000毫秒是关闭偏向锁的, 或者设置-XX:BiasedLockingStartupDelay=0
//        testCASBiasedLockWithoutRace();
//        testRevokeBiasedLock();
        testRevokeBiasedLockWhichStillInSync();
    }


}
