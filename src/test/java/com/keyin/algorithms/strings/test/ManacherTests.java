package com.keyin.algorithms.strings.test;

import com.keyin.algorithms.strings.Manacher;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ManacherTests {

    @Test
    public void testGetLongestPalindromicSubstring() throws Exception {
        final List<Object[]> data = Arrays.asList(
                new Object[][]{
                        {null, null},
                        {"", ""},
                        {"a", "a"},
                        {"aa", "aa"},
                        {"aaa", "aaa"},
                        {"abaa", "aba"},
                        {"abba", "abba"},
                        {"abbaaa", "abba"},
                        {"abbabb", "bbabb"},
                        {"bananas", "anana"},
                        {"bakskskba", "ksksk"},
                        {"itisneveroddoreven", "neveroddoreven"},
                        {"ABCDEFGHIJKLMNOPQRSTUVWXYZ", "A"},
                        {"I like bananas", "anana"}

                }
        );
        for (Object[] testCase: data) {
            String input = (String) testCase[0];
            String expected = (String) testCase[1];
            String result = Manacher.getLongestPalindromicSubstring(input);
            assertEquals(expected, result);
        }
    }
}
