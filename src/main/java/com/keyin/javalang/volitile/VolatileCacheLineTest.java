package com.keyin.javalang.volitile;

import java.util.concurrent.CountDownLatch;

/**
 * @description: 打开注释，关闭注释，运行时间不一样，因为有cpu cache,每次会拿64Byte 的数据。测试代码里两个对象
 * 前后如果加上7个long, 那么就大概率将将两个x分在不同的cacheline(主存中64Byte)，每个对象中，7*8+ 8 + 7* 8,
 * 
 * @author: yinke
 * @create: 2021-04-19 21:46
 **/
public class VolatileCacheLineTest {
    public static long COUNT = 1_0000_0000L;
    
    
    private static class T {
//        public long p1, p2, p3, p4,p5,p6,p7;
        public  volatile long x = 0L;
//        public long p9, p10,p11,p12,p13,p14,p15;
    }
    public static T[] arr = new T[2];
    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        
        Thread t1 = new Thread(()->{
            for (long i  = 0;i< COUNT;i++) {
                arr[0].x = i;
            }
            latch.countDown();
        });
        Thread t2 = new Thread(()->{
            for (long i  = 0;i< COUNT;i++) {
                arr[1].x = i;
            }
            latch.countDown();
        });
        final long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        latch.await();
        System.out.println(System.currentTimeMillis() - start);
        
    }
}
