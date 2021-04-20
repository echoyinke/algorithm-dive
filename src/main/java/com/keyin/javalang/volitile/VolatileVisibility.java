package com.keyin.javalang.volitile;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-19 22:17
 **/
public class VolatileVisibility {
    private static volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop) {
                    
                }
                System.out.println("receive stop signal");

            }
        });
        t.start();
        Thread.sleep(3000);
        stop = true;
        
    }
    
}
