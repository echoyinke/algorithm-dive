package com.keyin.algorithms.data_structures.test;

import com.keyin.algorithms.data_structures.TreeMap;
import com.keyin.algorithms.data_structures.test.common.JavaMapTest;
import com.keyin.algorithms.data_structures.test.common.MapTest;
import com.keyin.algorithms.data_structures.test.common.Utils;
import com.keyin.algorithms.data_structures.test.common.Utils.TestData;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TreeMapTests {

    @Test
    public void testTreeMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "TreeMap";
        TreeMap<String,Integer> map = new TreeMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                                   data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName,
                                           data.unsorted, data.sorted, data.invalid));
    }
}
