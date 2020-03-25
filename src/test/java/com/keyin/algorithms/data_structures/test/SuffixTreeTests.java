package com.keyin.algorithms.data_structures.test;

import com.keyin.algorithms.data_structures.SuffixTree;
import com.keyin.algorithms.data_structures.test.common.SuffixTreeTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SuffixTreeTests {

    @Test
    public void testSuffixTree() {
        String bookkeeper = "bookkeeper";
        SuffixTree<String> tree = new SuffixTree<String>(bookkeeper);
        assertTrue(SuffixTreeTest.suffixTreeTest(tree, bookkeeper));
    }
}
