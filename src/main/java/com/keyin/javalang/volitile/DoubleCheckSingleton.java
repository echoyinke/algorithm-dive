package com.keyin.javalang.volitile;

import lombok.SneakyThrows;

import java.security.PublicKey;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-19 22:53
 **/
public class DoubleCheckSingleton {
      // 非懒加载
    private static DoubleCheckSingleton INSTANCE2  = new DoubleCheckSingleton();
      public static DoubleCheckSingleton getInstance1() throws InterruptedException {
          return INSTANCE2;
      }
    
    // 非线程安全
    private static DoubleCheckSingleton INSTANCE;
    public static DoubleCheckSingleton getInstance2() throws InterruptedException {
        if (INSTANCE == null) {
            Thread.sleep(100);
            INSTANCE = new DoubleCheckSingleton();
        }
        return INSTANCE;
    }

    // 线程安全,但是太重
    public static DoubleCheckSingleton getInstance3() throws InterruptedException {
        synchronized (DoubleCheckSingleton.class) {
            if (INSTANCE == null) {
                Thread.sleep(100);
                INSTANCE = new DoubleCheckSingleton();
            }
            return INSTANCE;
        }
      
    }
    
    // 非线程安全,单check
    public static DoubleCheckSingleton getInstance4() throws InterruptedException {
        if (INSTANCE == null) {
            synchronized (DoubleCheckSingleton.class) {
                Thread.sleep(100);
                INSTANCE = new DoubleCheckSingleton();
            }
            return INSTANCE;
        }
        return INSTANCE;

    }
    // 这种方式有个隐患（可能几百万次才复现），就是指令重排序会导致对象new 的过程重排序 正常顺序：1. 内存分配，2. 构造 3. 指针赋给引用
    // 所以要加volatile
    private static volatile DoubleCheckSingleton INSTANCE5;
    public static DoubleCheckSingleton getInstance5() throws InterruptedException {
        if (INSTANCE5 == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (INSTANCE5 == null) {
                    Thread.sleep(100);
                    INSTANCE5 = new DoubleCheckSingleton();
                }
            }
            return INSTANCE5;
        }
        return INSTANCE5;
    }




    public static void main(String[] args) {
        for (int i = 0; i < 1000 ; i++) {
              new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println(DoubleCheckSingleton.getInstance5().hashCode());
                }
            }).start(); 
            
        }
      
    }
}
