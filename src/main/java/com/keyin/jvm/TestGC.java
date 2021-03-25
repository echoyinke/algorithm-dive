package com.keyin.jvm;

/**
 * @description: few experiments to test GC
 * 实验1：通过上面的GC日志我们可以看出一开始出现了 MinorGC, 引起GC的原因是 内存分配失败，因为分配allocation4的时候，
 * Eden区已经没有足够的区域来分配了，所以发生了本次 MinorGC ，经过 MinorGC 之后新生代的已使用容量从7989K减少到了424K，
 * 然而整个堆的内存总量却几乎没有减少，原因就是，由于发现新生代没有可以回收的对象，所以不得不使用内存担保将allocation1～3 三个对象提前转移到
 * 老年代。此时再在 Eden 区域为 allocation4 分配 4MB 的空间，因此最后我们发现 Eden 区域占用了 4MB，老年代占用了 6MB
 * Note by yinke: 所以ParNew的话，
 * 
 * 实验2：第一眼看到日志惊奇的发现居然没有发生任何类型的 GC， 起初看到日志的时候我也很惊奇，于是乎请出了Java内存分析工具VisualVM, JDK中会
 * 自带JVisualVM 效果类似，可以通过命令jvisualvm启动，具体分析方法可以参考我的另外一篇博客VisualVM使用指南。
 *
 * 注： 如果想要重现下面的分析结果，除了JVM参数要和我一直外，在程序执行过程中务必设置几个断点，好让VisualVM能够出现这种阶梯图像，不然由于
 * 执行太快，看起来就像是一次性分配了内存一样。
 * 通过上图中我标出的五条线，可以看到在1位置的时候第一次在Eden中分配了 2MB 内存，然后在2，3，4 时刻分别又再次分配了 2MB 内存，此时 Eden 
 * 区域已经占用了 8MB 了，在 5 时刻当要分配 4MB 内存的时候，Eden 区域已经不够用了，直接分配到了老年代。这是因为Parallel Scavenge收集器
 * 为了保证吞吐量而直接将Eden区中无法存放的大对象直接放到了老年代。
 *
 * 注： 虚拟机参数 -XX：PretenureSizeThreshold 并不是实验二中的大对象分配到老年代的直接控制参数，该参数只针对 Serial 和 ParNew 两个
 * 新生代收集器有作用。 该参数的作用在下一节大对象直接进入老年代的实验中讲解。
 * 
 * 
 * 实验3：虚拟机提供一个参数 -XX:PretenureSizeThreshold 用来设置直接在老年代分配的对象的大小，如果对象大于这个值就会直接在老年代分配。
 * 这样做的目的是避免在Eden区及两个Survivor区之间发生大量的内存复制。
 * “比遇到一个大对象更加坏的消息就是遇到一群“朝生夕灭”的“短命大对象”，写程序的时候应当避免”
 *
 * 实验4：通过上面的堆内存分配和visualGC的分析图片可以看出，当执行到最后一句话的时候，也就是进行第二次GC的时候，原本在S0区域的一些对象被移
 * 动到了老年代，这是因为当发生MinorGC的时候发现该对象的年龄已经到达我们设置的MaxTenuringThreshold值。
 * 
 * 实验5：通过上面的日志和图片，可以看出与实验四不同的是，这里执行到最后的时候S0区域中的对象并没有进入到老年代而是进入了S1区域,因为我们把进
 * 入老年代的年龄设置为了4.
 *
 * 注：如果没有按照我的代码写的话，你可能会发现即使把MaxTenuringThreshold设置为了大于1的值，但还是有可能会在一次 GC 之后就进入了老年代，
 * 这是因为进入我们设置的只是一个最大值，真正的值可能比这个值更小，是动态变化的，这种情况会在下面的实验中介绍。
 * 实验6：看到上面的堆信息可以看到最后from和to两个Survivor区域占用情况都为0，原因就是在allocation1被放到S1区域之后，由于该对象和一些
 * 附加信息的和占用了S1区域一半以上的空间，因为此时allocation1对象的年龄为1，所以此时TenuringThreshold的值也就变成了1，在GC的时候进入
 * 了老年代。通过上图中VisualGC左下角的TenuringThreshold值一直在变化也能得到验证。
 * 
 * Noted by yinke:
 *-Xms Initial java heap size.
 * -Xmn Minimum java heap size.
 * -Xmx Maximum java heap size.
 * -XX:PermSize The default value is 64MB for a server JVM. Setting it to a more appropriate value eliminates the overhead of increasing this part of the heap.
 * -XX:MaxPermSize Maximum size of the permanent generation.
 * -XX:NewSize Size of the new generation.
 * -XX:MaxNewSize Maximum size of the new generation.
 * 能和CMS配合的最好的就是ParNew，ParNew是copying算法，所以也就是survivor.
 * 
 * 
 * 
 * 
 * @author: yinke
 * @create: 2020-11-17 22:23
 **/
public class TestGC {

    /**
     * Xmn: max new(young) gene
     * Xmx: max heap size 
     */

    private static final int _1MB = 1024*1024;
    /**
     * VM参数：（参数序号对应实验序号）
     * -XX:+UseParNewGC	使用 ParNew + Serial Old 收集器组合进行内存回收（如果使用visualVM请调大内存，visualVM会占用一定的Eden： -verbose:gc -Xms20M -Xmx26M -Xmn13M）
     *  1. -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseParNewGC -XX:+PrintGCDetails -XX:SurvivorRatio=8 
     *  -XX:+UseParallelGC 使用 Parallel Scavenge + Serial Old （PS MarkSweep）的收集器组合
     *  2. -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseParallelGC -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2*_1MB];
        allocation2 = new byte[2*_1MB];
        allocation3 = new byte[2*_1MB];
        allocation4 = new byte[4*_1MB]; //出现一次 Minor GC
    }

    /**
     * VM参数：（参数序号对应实验序号）
     *  3. -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseParNewGC -XX:+PrintGCDetails -XX:PretenureSizeThreshold=3145728
     */
    public static void testPreteureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];
    }

    /**
     * VM参数：（参数序号对应实验序号）
     *  4. -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     *  5. -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:MaxTenuringThreshold=4 -XX:+PrintTenuringDistribution
     *
     */
    @SuppressWarnings("unused")
    public static void testTenuringThredhold() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 16];
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

    /**
     * VM参数：（参数序号对应实验序号）
     *  6. -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:MaxTenuringThreshold=4 -XX:+PrintTenuringDistribution
     *
     *  如果在Survivor空间中相同年龄所有对象大小的总和>Survivor空间的一半（ -XX:TargetSurvivorRatio）时，年龄>=该年龄的对象就可以直接进入年老代
     */
    @SuppressWarnings("unused")
    public static void testTenuringThredhold2() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 4];
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testTenuringThredhold();
    }
}
