package com.keyin.javalang.multithread;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
    
    /*方法一: 信号量*/
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
    /*方法一: cyclicBarrier*/
    static class Foo3 {
        CountDownLatch countDownLatch12 = new CountDownLatch(1);
        CountDownLatch countDownLatch23 = new CountDownLatch(1);
        public Foo3() {

        }

        public void first(Runnable printFirst) throws InterruptedException {
            
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            countDownLatch12.countDown();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            // printSecond.run() outputs "second". Do not change or remove this line.
            countDownLatch12.await();
            printSecond.run();
            countDownLatch23.countDown();
        }

        public void third(Runnable printThird) throws InterruptedException {
            countDownLatch23.await();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            countDownLatch23.countDown();
        }
    }
    
    
    /* 方法二: lock 锁 */
    static class Foo2 {

        int num = 1;
        ReentrantLock lock;
        //精确的通知和唤醒线程
        Condition condition1, condition2, condition3;

        public Foo2() {
            lock = new ReentrantLock();
            condition1 = lock.newCondition();
            condition2 = lock.newCondition();
            condition3 = lock.newCondition();
        }

        public void first(Runnable printFirst) throws InterruptedException {
              
                // printFirst.run() outputs "first". Do not change or remove this line.
            while (num != 1) {
                lock.lock();
                try {
                    condition1.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            printFirst.run();
            num = 2;
          
        }

        public void second(Runnable printSecond) throws InterruptedException {
            while (num != 2) {
                lock.lock();
                try {
                    condition2.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            num = 3;
            condition2.signal();
            
        }

        public void third(Runnable printThird) throws InterruptedException {
            lock.lock();
            try {
                condition3.await();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            
            // printSecond.run() outputs "second". Do not change or remove this line.
            printThird.run();
            num = 1;
            condition3.signal();
        }
    }


    static class Foo4 {
        BlockingQueue<String> blockingQueue12, blockingQueue23;

        public Foo4() {
            //同步队列,没有容量，进去一个元素，必须等待取出来以后，才能再往里面放一个元素
            blockingQueue12 = new SynchronousQueue<>();
            blockingQueue23 = new SynchronousQueue<>();
        }

        public void first(Runnable printFirst) throws InterruptedException {

            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            blockingQueue12.put("stop");
        }

        public void second(Runnable printSecond) throws InterruptedException {
            blockingQueue12.take();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            blockingQueue23.put("stop");
        }

        public void third(Runnable printThird) throws InterruptedException {
            blockingQueue23.take();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }
    


    

    public static void main(String[] args) {
        
        Foo2 foo = new Foo2();
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
                foo.second(() -> { System.out.println("second");});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            try {
                foo.third(() -> { System.out.println("third");});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
       
    }
    
}


