package com.keyin.algorithms.data_structures.test;

import com.keyin.algorithms.data_structures.test.common.JavaCollectionTest;
import com.keyin.algorithms.data_structures.test.common.TreeTest;
import com.keyin.algorithms.data_structures.test.common.Utils;
import com.keyin.algorithms.data_structures.PatriciaTrie;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class PatriciaTreeTests {

    @Test
    public void testPatriciaTrie() {
        Utils.TestData data = Utils.generateTestData(1000);

        String bstName = "PatriciaTrie";
        PatriciaTrie<String> bst = new PatriciaTrie<String>();
        Collection<String> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, String.class, bstName,
                                     data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, String.class, bstName,
                                                     data.unsorted, data.sorted, data.invalid));
    }
}
