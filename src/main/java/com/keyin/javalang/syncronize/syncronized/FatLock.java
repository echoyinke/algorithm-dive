package com.keyin.javalang.syncronize.syncronized;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * @description: 重量级锁标记：010  
 * 
 *   1.轻量级升级成重量级 2. 已经是重量级锁了，再次被获取
 * @author: yinke
 * @create: 2021-04-25 14:42
 **/
public class FatLock {
    
    private static Logger log = LoggerFactory.getLogger(FatLock.class);
    public static Lock lock = new Lock();
    private static Thread thread1, thread2, thread3;

    public static void testAcquireFatLockFromBiaseLock() throws InterruptedException {
        thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lock) {
                    log.info("thread 1 acquire the lock\n"+ ClassLayout.parseInstance(lock).toPrintable());
                }
            }
        });
        thread2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lock) {
                    log.info("thread 2 acquire with race the lock\n" + ClassLayout.parseInstance(lock).toPrintable());
                }
            }
        });
        thread1.start();
        thread2.start();
        thread2.join();
        
    }
    public static void testAcquireFatLockFromThinLock() throws InterruptedException {
        thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lock) {
                    log.info("thread 1 acquire the lock\n"+ ClassLayout.parseInstance(lock).toPrintable());
                }
            }
        });
        
        thread2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(1000);
                synchronized (lock) {
                    log.info("thread 2 acquire with race the lock\n" + ClassLayout.parseInstance(lock).toPrintable());
                    Thread.sleep(3000);
                }
            }
        });
        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    log.info("thread 3 acquire with race the lock\n" + ClassLayout.parseInstance(lock).toPrintable());
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread2.join();
    }
    

    public static void testAcquireFatLockWhichAlreadyFat() throws InterruptedException {
        thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
               synchronized (lock) {
                   log.info("thread 1 acquire the lock\n"+ ClassLayout.parseInstance(lock).toPrintable());
               }
            }
        });
        thread2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lock) {
                    log.info("thread 2 acquire with race the lock\n" + ClassLayout.parseInstance(lock).toPrintable());
                }
            }
        });
        thread1.start();
        
//        Thread.sleep(1000);
        thread2.start();
        thread2.join();
        Thread.sleep(5000);
        log.info("after release lock. fat lock wont change mark world. last 2 bit 10\n" + ClassLayout.parseInstance(lock).toPrintable());
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    log.info("thread 3 acquire a already fat lock without race\n" + ClassLayout.parseInstance(lock).toPrintable());
                }  
            }
        });
        thread3.start();
        thread3.join();

    }


    public static void main(String[] args) throws InterruptedException {
//        testAcquireFatLockWhichAlreadyFat();
        testAcquireFatLockFromThinLock();
    }
}
