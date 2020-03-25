package com.keyin.algorithms.data_structures.test;

import com.keyin.algorithms.data_structures.RadixTrie;
import com.keyin.algorithms.data_structures.test.common.JavaMapTest;
import com.keyin.algorithms.data_structures.test.common.MapTest;
import com.keyin.algorithms.data_structures.test.common.Utils;
import com.keyin.algorithms.data_structures.test.common.Utils.TestData;
import junit.framework.Assert;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RadixTrieTests {

    @Test
    public void testRadixTrie() {
        TestData data = Utils.generateTestData(1000);
        runTests(data);
    }
 
    /** This was an error condition previously in converting a black node with children into a white terminating node **/
    @Test
    public void cornerCase() {
        RadixTrie<String,Integer> map = new RadixTrie<String,Integer>();
        map.put("1", 1);
        map.put("112", 112);
        map.put("113", 1123);
        map.put("11", 11);
        map.remove("11");
        Integer r = map.put("11", 11);
        Assert.assertTrue(r==null);
    }

    private void runTests(@NotNull TestData data) {
        String mapName = "RadixTrie";
        RadixTrie<String,Integer> map = new RadixTrie<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                                   data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, String.class, mapName,
                                           data.unsorted, data.sorted, data.invalid));
    }
}
