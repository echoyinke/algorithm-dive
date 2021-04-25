package com.keyin.javalang.syncronize.syncronized;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @description: -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0 -XX:+PrintCommandLineFlags -XX:+TraceBiasedLocking -XX:+PrintSafepointStatistics * @see "https://zhuanlan.zhihu.com/p/302874340"
 * @author: yinke
 * @create: 2021-04-23 11:20
 * @see "https://github.com/farmerjohngit/myblog/issues/13"
 * @see "https://zhuanlan.zhihu.com/p/302874340"
 * 批量重偏向，分为两步：
 * <p>
 * code 1 将类中的撤销计数器自增1，之后当该类已存在的实例获得锁时，就会尝试重偏向，相关逻辑在偏向锁获取流程小节中。
 * <p>
 * code 2 处理当前正在被使用的锁对象，通过遍历所有存活线程的栈，找到所有正在使用的偏向锁对象，然后更新它们的epoch值。也就是说不会重偏向正在使用的锁，否则会破坏锁的线程安全性。
 **/
public class BulkRebiaseOrBulkRevoke {
    private static Logger log = LoggerFactory.getLogger(BulkRebiaseOrBulkRevoke.class);


    /*批量重偏向的阈值，当某个类的对象，超过20个出现撤销，那么将进行批量重偏*/
    public static int bulkRebiaseThreshold = 21;
    public static int bulkRevokeBiasedLockThreshold = 41;

    /*第20次锁撤销之后，会进行批量锁重偏，所以线程2的第20-39又变成偏向锁了*/
    public static void testRebias() throws InterruptedException {
        List<Lock> objList = new ArrayList<>();

        Thread thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("new {} object and lock them with thread 1", bulkRebiaseThreshold);
                for (int i = 0; i < bulkRebiaseThreshold; i++) {
                    Lock lock = new Lock();
                    objList.add(lock);
                    synchronized (lock) {
                    }
                }
                log.info("all object should be biased to thread 1\n{}", ClassLayout.parseInstance(objList.get(bulkRebiaseThreshold - 1)).toPrintable());

            }
        });
        Thread threadProof = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (objList.get(20)) {
                    log.info("trying to acquire lock of 20th obj\n{}", ClassLayout.parseInstance(objList.get(20)).toPrintable());
                    LockSupport.park();
                }
                synchronized (objList.get(20)) {
                    log.info("trying to acquire lock of 20th obj\n{}", ClassLayout.parseInstance(objList.get(20)).toPrintable());
                }
                
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("<====biasedLock re-bias experiment====>\n\n");
                for (int i = 1; i < bulkRebiaseThreshold; i++) {
                    Lock lock = objList.get(i);
                    if (i == 19) {
                        log.info("before re-bias we peek the object.\n {}", ClassLayout.parseInstance(objList.get(i)).toPrintable());
                    }
                    synchronized (lock) {
//                        log.info("the {}th loop", i);
//                        log.info(ClassLayout.parseInstance(lock).toPrintable());
                    }
                    if (i == 19) {
                        log.info("in loop {}, after re-bias we peek the  object.\n {}", i, ClassLayout.parseInstance(objList.get(i)).toPrintable());
                        log.info("in loop {}, after re-bias we peek the next object.\n {}", i, ClassLayout.parseInstance(objList.get(i + 1)).toPrintable());
                        // 此时只会修改类对象的epoch和处于加锁中的锁对象的epoch（也就是说不会重偏向处于使用中的锁对象）,所以此时看第i+1个对象暂时还是偏向thread1
                        break;
                    }
                }
                LockSupport.unpark(threadProof);
            }
        });
   


        thread1.start();
        Thread.sleep(3000);
        threadProof.start();
        thread2.start();
        Thread.sleep(3000);
        System.gc();
        log.info(" after gc peek the next object.\n {}", ClassLayout.parseInstance(objList.get(20)).toPrintable());


    }

    public static void main(String[] args) throws InterruptedException {
        testRebias();
    }


}
