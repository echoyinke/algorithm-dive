package com.keyin.algorithms.strings;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 *给定一个字符串，逐个翻转字符串中的每个单词。
 *
 *
 *
 * 示例 1：
 *
 * 输入: "the sky is blue"
 * 输出: "blue is sky the"
 *
 * 输入: "  hello world!  "
 * 输出: "world! hello"
 *
 * **/
public class ReverseWords {

public static final String reverseWords(String s) {
    // 除去开头和末尾的空白字符
    s = s.trim();
    // 正则匹配连续的空白字符作为分隔符分割
    String[] strings = s.split("\\s{1,}");
    List<String> wordList = Arrays.asList(strings);
    Collections.reverse(wordList);
    return String.join(" ", wordList);


}
    public static void main(String[] args) {
        System.out.println(reverseWords("  hello world!  "));
    }
}
