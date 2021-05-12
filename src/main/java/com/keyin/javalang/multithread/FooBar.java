package com.keyin.javalang.multithread;

import lombok.SneakyThrows;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 
 *   public int getN() {
 *         return n;
 *     }
 *
 *     public void setN(int n) {
 *         this.n = n;
 *     }
 *
 *     public int n = 0;
 *
 *     public void foo() {
 *         for (int i=0;i<n;i++) {
 *             System.out.println("foo");
 *         }
 *     }
 *     public void bar() {
 *         for (int i=0;i<n;i++) {
 *             System.out.println("bar");
 *         }
 *     }两个线程公用一个FooBar实例， 按顺序打印
 * 
 * 两个不同的线程将会共用一个 FooBar实例。其中一个线程将会调用foo()方法，另一个线程将会调用bar()方法。
 * 
 *
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。

 * @author: yinke
 * @create: 2021-03-22 23:09
 **/
public class FooBar {
    AtomicInteger fooAlreadyPrinted = new AtomicInteger(0);
    AtomicInteger barAlreadyPrinted = new AtomicInteger(1);

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int n = 0;
    
    public void foo() {
        for (int i=0;i<n;i++) {
            while (barAlreadyPrinted.get() != 1) {
                
            }
                System.out.print("foo");
                fooAlreadyPrinted.incrementAndGet();
                barAlreadyPrinted.decrementAndGet();
            
        }
    }
    public void bar() {
        for (int i=0;i<n;i++) {
            while (fooAlreadyPrinted.get() != 1) {
                
            }
                System.out.print("bar");
                barAlreadyPrinted.incrementAndGet();
                fooAlreadyPrinted.decrementAndGet();
            
        }
    }

    /*方法2：使用CirclicBarrier*/
    
    public CyclicBarrier cyclicBarrier  = new CyclicBarrier(2);
    public boolean foo2Print = true;
    
    public void foo1() throws BrokenBarrierException, InterruptedException {
        for (int i=0;i<n;i++) {
            while (!foo2Print) {
                
            }
            System.out.print("foo");
            foo2Print = false;
            cyclicBarrier.await();
            

        }
    }

    public void bar1() throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < n; i++) {
            cyclicBarrier.await();
            System.out.print("bar");
            foo2Print = true;
        }
    }

    /*方法3：使用Semaphore*/

    public Semaphore foo  = new Semaphore(1);
    public Semaphore bar  = new Semaphore(0);


    public void foo2() throws BrokenBarrierException, InterruptedException {
        for (int i=0;i<n;i++) {
            foo.acquire();
            System.out.print("foo");
            bar.release();
            


        }
    }

    public void bar2() throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.acquire();            
            System.out.print("bar");
            foo.release();
        }
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar();
        fooBar.setN(5);
 
        Executor executor = Executors.newFixedThreadPool(2);
        executor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                fooBar.foo1(); 
            }
        });
        executor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                fooBar.bar1();
            }
        });

     
    }
}
