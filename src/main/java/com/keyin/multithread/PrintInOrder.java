package com.keyin.multithread;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @description: 三个不同的线程 A、B、C 将会共用一Foo实例。
 * <p>
 * 一个将会调用 first() 方法
 * 一个将会调second() 方法
 * 还有一个将会调用 third() 方法
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
 * class Foo {
 * <p>
 * public Foo() {
 * <p>
 * }
 * <p>
 * public void first(Runnable printFirst) throws InterruptedException {
 * <p>
 * // printFirst.run() outputs "first". Do not change or remove this line.
 * printFirst.run();
 * }
 * <p>
 * public void second(Runnable printSecond) throws InterruptedException {
 * <p>
 * // printSecond.run() outputs "second". Do not change or remove this line.
 * printSecond.run();
 * }
 * <p>
 * public void third(Runnable printThird) throws InterruptedException {
 * <p>
 * // printThird.run() outputs "third". Do not change or remove this line.
 * printThird.run();
 * }
 * }
 * 示例 1:
 * <p>
 * 输入: [1,2,3]
 * 输出: "firstsecondthird"
 * 解释:
 * 有三个线程会被异步启动。
 * 输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，线程 C 将会调用 third() 方法。
 * 正确的输出是 "firstsecondthird"。
 * 示例 2:
 * <p>
 * 输入: [1,3,2]
 * 输出: "firstsecondthird"
 * 解释:
 * 输入 [1,3,2] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 third() 方法，线程 C 将会调用 second() 方法。
 * 正确的输出是 "firstsecondthird"。
 * @author: yinke
 * @create: 2021-03-22 23:15
 **/
public class PrintInOrder {
    static class Foo {
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(1);
        public Foo() {

        }

        public void first(Runnable printFirst) throws InterruptedException {

            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            semaphore1.acquire();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            semaphore1.release();
        }

        public void third(Runnable printThird) throws InterruptedException {
            semaphore2.acquire();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            semaphore2.release();
        }
    }
    

    public static void main(String[] args) {
        Foo foo = new Foo();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> {
            try {
                foo.first(() -> { System.out.println("first");});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            try {
                foo.first(() -> { System.out.println("second");});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            try {
                foo.first(() -> { System.out.println("third");});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
       
    }
    
}


