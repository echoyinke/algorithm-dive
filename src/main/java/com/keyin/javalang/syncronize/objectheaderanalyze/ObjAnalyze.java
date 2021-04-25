package com.keyin.javalang.syncronize.objectheaderanalyze;

import org.openjdk.jol.info.ClassLayout;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-20 17:20
 **/
public class ObjAnalyze {
    public static class A {
    }

    public static class B {
        private long s;
    }

    public static class C {
        private int a;
        private long s;
    }

    int[] aa = new int[0];

    public static void main(String[] args) {
        A a = new A();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        B b = new B();
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
        C c = new C();
        System.out.println(ClassLayout.parseInstance(c).toPrintable());
        int[] aa = new int[0];
        System.out.println(ClassLayout.parseInstance(aa).toPrintable());
        
        
        System.out.println(ClassLayout.parseInstance(new Integer(1)).toPrintable());
        System.out.println(ClassLayout.parseInstance(new Boolean(true)).toPrintable());
        


    }
}


