package com.keyin.algorithms.data_structures.test;

import com.keyin.algorithms.data_structures.test.common.JavaMapTest;
import com.keyin.algorithms.data_structures.test.common.MapTest;
import com.keyin.algorithms.data_structures.test.common.Utils;
import com.keyin.algorithms.data_structures.HashMap;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HashMapTests {

    @Test
    public void testHashMap() {
        Utils.TestData data = Utils.generateTestData(1000);

        String mapName = "ProbingHashMap";
        HashMap<Integer,String> map = new HashMap<Integer,String>(HashMap.Type.PROBING);
        java.util.Map<Integer,String> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, Integer.class, mapName, data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName, data.unsorted, data.sorted, data.invalid));

        mapName = "LinkingHashMap";
        map = new HashMap<Integer,String>(HashMap.Type.CHAINING);
        jMap = map.toMap();

        assertTrue(MapTest.testMap(map, Integer.class, mapName, data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName, data.unsorted, data.sorted, data.invalid));
    }
}
