package com.keyin.algorithms.data_structures.test;

import com.keyin.algorithms.data_structures.BinarySearchTree;
import com.keyin.algorithms.data_structures.Treap;
import com.keyin.algorithms.data_structures.test.common.JavaCollectionTest;
import com.keyin.algorithms.data_structures.test.common.TreeTest;
import com.keyin.algorithms.data_structures.test.common.Utils;
import com.keyin.algorithms.data_structures.test.common.Utils.TestData;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class TreapTests {

    @Test
    public void testTreap() {
        TestData data = Utils.generateTestData(1000);

        String bstName = "Treap";
        BinarySearchTree<Integer> bst = new Treap<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Integer.class, bstName,
                                     data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, Integer.class, bstName,
                                                     data.unsorted, data.sorted, data.invalid));
    }
}
