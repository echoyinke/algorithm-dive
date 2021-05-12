package com.keyin.javalang.hashmap;


import java.util.HashMap;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-26 17:21
 **/
public class HashMapTest {

/**
 * 之前提到过 & 运算只会关注n – 1（n =数组长度）的有效位，当扩容之后，n的有效位相比之前会多增加一位（n会变成之前的二倍，
 * 所以确保数组长度永远是2次幂很重要），然后只需要判断hash在新增的有效位的位置是0还是1就可以算出新的索引位置，如果是0，那么索引没有发生变化，
 * 如果是1，索引就为原索引加上扩容前的容量。
 * 
 * 
 * **/



public static void testResize() {
    HashMap<Integer, String> map = new HashMap<>(4);
    map.put(1, "a");
    map.put(2, "b");
    map.put(3, "c");
    map.put(4, "d");
    // 查看map中table的size
}

    public static void main(String[] args) {
        HashMap<Integer, String> map =new HashMap<>(4);
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        map.put(4,"d");
        int modRes = 1%4;// 01
        int modRes2 = 5%4; // 101
        int andRes1 = 1&(4-1);// 1
        int andRes2 = 5&(8-1);// 101&111 = 101 = 5, 所以只看新增的1就可以按2次方移动
        
    }
}
