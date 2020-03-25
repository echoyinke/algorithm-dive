package com.keyin.algorithms.data_structures.test;

import com.keyin.algorithms.data_structures.SkipListMap;
import com.keyin.algorithms.data_structures.test.common.JavaMapTest;
import com.keyin.algorithms.data_structures.test.common.MapTest;
import com.keyin.algorithms.data_structures.test.common.Utils;
import com.keyin.algorithms.data_structures.test.common.Utils.TestData;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SkipListMapTests {

    @Test
    public void testSkipListMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "SkipListMap";
        SkipListMap<String,Integer> map = new SkipListMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                                   data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName,
                                           data.unsorted, data.sorted, data.invalid));
    }

}
