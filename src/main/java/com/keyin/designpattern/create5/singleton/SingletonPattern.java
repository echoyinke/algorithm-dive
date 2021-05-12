package com.keyin.designpattern.create5.singleton;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-28 15:17
 **/
public class SingletonPattern {


    private static volatile SingletonPattern singletonPattern;

    /*lazy load*/
    public static synchronized SingletonPattern getSingletonPatternInstance() {
        if (singletonPattern == null) {
            singletonPattern = new SingletonPattern();
        }
        return singletonPattern;
    }

    /* double check singleton*/
    public static SingletonPattern getSingletonPatternInstanceDCS() {
        
        if (singletonPattern == null) {
            synchronized (SingletonPattern.class) {
                //可能之前被另外一个进入if (singletonPattern == null) 的建立了
                if (singletonPattern == null) {
                    singletonPattern = new SingletonPattern();
                }
            }
        }
        return singletonPattern;
    }

}
