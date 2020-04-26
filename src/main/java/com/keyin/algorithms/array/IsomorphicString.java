package com.keyin.algorithms.array;


/**
 * 205. 同构字符串
 * 给定两个字符串 s 和 t，判断它们是否是同构的。
 *
 * 如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
 *
 * 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
 *
 * **/

public class IsomorphicString {


    public static boolean isIsomorphic(String s, String t) {
        return canMap(s, t) && canMap(t, s);
    }

    private  static boolean canMap(String s, String t){
        int[] map = new int[128]; // ascii 128个字符
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        char c = chars1[0];
        for(int i = 0; i < chars1.length; i++){
            // 当前槽位没被使用过
            if(map[chars1[i]] == 0)
                // 将 s 的对应 char 放入槽位
                map[chars1[i]] = chars2[i];
            else {// 当前槽位已经被占用，也即s 中有重复 char
                // 此时 t 中的 char 必须也是重复，跟 s 中对应的位置一样才行
                if(map[chars1[i]] != chars2[i])
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        isIsomorphic("foo", "bar");

    }

}
