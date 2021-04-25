package com.keyin.javalang.syncronize.syncronized;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * @description:  轻量级锁： 000
 * 
 * 不同的线程在不同的时间去抢锁，没有竞争。重入是通过遍历栈里的lock record（对象头里的指针指向lock record）
 *                判断要不要加轻量级锁
 * @author: yinke
 * @create: 2021-04-25 09:48
 **/
public class ThinLockTest {
    private static Logger log = LoggerFactory.getLogger(ThinLockTest.class);
    
    public static Lock lock = new Lock();
    
    public static void testThinLock() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lock) {
                    log.info("thread 1 acquire the lock.");
                    log.info(ClassLayout.parseInstance(lock).toPrintable());
                    synchronized (lock) {
                        log.info("thread 1 acquire the lock again\n" + ClassLayout.parseInstance(lock).toPrintable());
                    }
                }
            }
        });
        
        Thread thread2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lock) {
                    log.info("thread 2 acquire the lock.\n" + ClassLayout.parseInstance(lock).toPrintable());
                }
            }
        });

        thread2.start();
        thread2.join();
        thread1.start();
        thread1.join();
        log.info("thread release the lock.\n" + ClassLayout.parseInstance(lock).toPrintable());
  
    }
    
    

    public static void main(String[] args) throws InterruptedException {
        testThinLock();
    }

}
