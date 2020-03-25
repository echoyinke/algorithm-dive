package com.keyin.algorithms.data_structures.test;

import com.keyin.algorithms.data_structures.SuffixTrie;
import com.keyin.algorithms.data_structures.test.common.SuffixTreeTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SuffixTrieTests {

    @Test
    public void testSuffixTrie() {
        String bookkeeper = "bookkeeper";
        SuffixTrie<String> trie = new SuffixTrie<String>(bookkeeper);
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, bookkeeper));
    }
}
