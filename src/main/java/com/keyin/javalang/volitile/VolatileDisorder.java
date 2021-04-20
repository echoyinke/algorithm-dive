package com.keyin.javalang.volitile;

/**
 * @description: 假设不会重排序，那么不管两个线程的之间怎么运行都不会出现x==0 && y ==0
 * @author: yinke
 * @create: 2021-04-19 22:26
 **/
public class VolatileDisorder {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;


    public static void main(String[] args) throws InterruptedException {
        int i= 0;
        for (;;) {
            i++;
            x = 0;y = 0;
            a= 0;b = 0;
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();;other.start();
            one.join();other.join();
            if (x==0 && y==0) {
                System.err.println("after " + i + " loop, x =" + x + " y=" +y);
            }
        }
    }
}
