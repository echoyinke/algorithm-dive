package com.keyin.javalang.threadlocal;

import static java.lang.Thread.sleep;

/**
 * @description: experiments to test threadlocal
 * @author: yinke
 * @create: 2020-11-17 22:24
 **/
public class ThreadLocalTest {
    private static ThreadLocal<byte[]> threadLocal = new ThreadLocal<>();

    /**
     * 启动命令: java -Xmx200m ThreadLocalTest， 此命令表示最大堆大小设置为200M
     * 
     * 此段代码执行过程中，先通过threadLocal为当前线程分配了一个100M的byte数组。 接下来的for循环会分配180MB的空间，如果在分配的过程中threadLocal关联的100M bytes被顺利回收，则分配可顺利进行(因为最大堆大小设置为200M)
     * @throws InterruptedException 
     */
    private static void memoryLeakTest() throws InterruptedException {
        sleep(20000);
        // thradLocal中存储了100M的数据，最大堆大小设置为200M.
        threadLocal.set(new byte[1024 * 1024 * 100]);
        sleep(100);
        //MARK1 threadLocal会被回收
        threadLocal = null;
        byte[][] bytes = new byte[20][];
        for (int i = 0; i < 18; i++) {
            bytes[i] = new byte[1024 * 1024 * 10];
            System.out.println((i + 1) * 10 + "Mb allocated..");
            sleep(2000);
        }
        System.out.println("finished..");
    }

    /**
     * 
     * 用完threadLocal以后，调用了一下threadLocal.remove() ，代码执行过程中我们打开jconsole
     *
     * 查看进程堆使用曲线如下，可以看出，在堆使用占用接近140M的时候有一次明显的GC过程，大约清理了100M的内存占用空间。也就是说，通过threadLocal分配的100M数据在这一次GC过程中
     * @throws InterruptedException 
     */
    private static void avoidMemoryLeakTest() throws InterruptedException {
        sleep(20000);
        // thradLocal中存储了100M的数据，最大堆大小设置为200M.
        threadLocal.set(new byte[1024 * 1024 * 100]);
        sleep(100);
        // 主动调用 threadLocal.remove();
        threadLocal.remove();
        // threadLocal会被回收
        threadLocal = null;
        byte[][] bytes = new byte[20][];
        for (int i = 0; i < 16; i++) {
            bytes[i] = new byte[1024 * 1024 * 10];
            System.out.println((i + 1) * 10 + "Mb allocated..");
            sleep(2000);
        }
        System.out.println("finished..");
    }
}
