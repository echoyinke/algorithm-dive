package com.keyin.algorithms.strings;


/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * 示例 1:
 *
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * 示例 2:
 *
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 *
 * **/

// 前两个的 lcs 再和第三个比，再和第四个比
public class LongestCommonPrefix {


    public static final String longestCommonStr(String[] strs) {

        if (strs == null || strs.length==0) {
            return "";
        }
        if (strs.length == 0) return "";
        String prefix = strs[0];
        String tmp = "";
        int j = 1;
        for (int i = 1; i < strs.length; i++) {
            int end = Math.min(prefix.length(), strs[i].length());
            while (j <= end ) {
                if (prefix.substring(0, j).equals(strs[i].substring(0, j))) {
                    tmp = prefix.substring(0,j);
                }
                j++;
            }
            j=0;
            prefix = tmp;
        }
        return prefix;

    }

    public static void main(String[] args) {
        String[] strs = new String[]{"flower","flow","flight"};
        longestCommonStr(strs);
    }

}
