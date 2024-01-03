/*
 * Copyright 2024 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.commons.collections;

import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("ConstantConditions")
class ListUtilsTest {

    @Test
    void testIntersection() {
        List<Integer> aList = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> bList = List.of(4, 5, 6, 7, 8, 9);

        List<Integer> check = ListUtils.intersection(aList, bList);
        Assertions.assertEquals(3, check.size());
        Assertions.assertTrue(check.contains(4));
        Assertions.assertTrue(check.contains(5));
        Assertions.assertTrue(check.contains(6));
    }

    @Test
    void testUnique() {
        List<Integer> aList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        List<Integer> bList = new ArrayList<>(List.of(4, 5, 6, 7, 8, 9));

        List<Integer> check = ListUtils.unique(aList, bList);
        Assertions.assertEquals(6, check.size());
        Assertions.assertTrue(check.contains(1));
        Assertions.assertTrue(check.contains(2));
        Assertions.assertTrue(check.contains(3));
        Assertions.assertTrue(check.contains(7));
        Assertions.assertTrue(check.contains(8));
        Assertions.assertTrue(check.contains(9));
    }

    @Test
    void testUnion() {
        List<Integer> aList = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> bList = List.of(4, 5, 6, 7, 8, 9);

        List<Integer> check = ListUtils.union(aList, bList);
        Assertions.assertEquals(9, check.size());
        Assertions.assertTrue(check.contains(1));
        Assertions.assertTrue(check.contains(2));
        Assertions.assertTrue(check.contains(3));
        Assertions.assertTrue(check.contains(4));
        Assertions.assertTrue(check.contains(5));
        Assertions.assertTrue(check.contains(6));
        Assertions.assertTrue(check.contains(7));
        Assertions.assertTrue(check.contains(8));
        Assertions.assertTrue(check.contains(9));
    }

    @Test
    void testMapKeyToList() {
        Map<Integer, String> map = new HashMap<>() {
            {
                put(1, "value1");
                put(2, "value2");
                put(3, "value3");
                put(4, "value4");
                put(5, "value5");
            }
        };

        List<Integer> check = ListUtils.mapKeyToList(map);
        Assertions.assertEquals(5, check.size());
        Assertions.assertTrue(check.contains(1));
        Assertions.assertTrue(check.contains(2));
        Assertions.assertTrue(check.contains(3));
        Assertions.assertTrue(check.contains(4));
        Assertions.assertTrue(check.contains(5));
    }

    @Test
    void testMapValuesToList() {
        Map<String, Integer> map = new HashMap<>() {
            {
                put("value1", 1);
                put("value2", 2);
                put("value3", 3);
                put("value4", 4);
                put("value5", 5);
            }
        };

        List<Integer> check = ListUtils.mapValuesToList(map);
        Assertions.assertEquals(5, check.size());
        Assertions.assertTrue(check.contains(1));
        Assertions.assertTrue(check.contains(2));
        Assertions.assertTrue(check.contains(3));
        Assertions.assertTrue(check.contains(4));
        Assertions.assertTrue(check.contains(5));
    }

    @Test
    void testIterate() {
        List<Integer> aList = List.of(1, 2, 3, 4, 5);

        List<Integer> check = ListUtils.iterate(aList.iterator());
        Assertions.assertEquals(5, check.size());
        Assertions.assertTrue(check.contains(1));
        Assertions.assertTrue(check.contains(2));
        Assertions.assertTrue(check.contains(3));
        Assertions.assertTrue(check.contains(4));
        Assertions.assertTrue(check.contains(5));
    }

    @Test
    void testToList() {
        Set<Integer> set = Set.of(1, 2, 3, 4, 5);

        List<Integer> check = ListUtils.toList(set);
        Assertions.assertEquals(5, check.size());
        Assertions.assertTrue(check.contains(1));
        Assertions.assertTrue(check.contains(2));
        Assertions.assertTrue(check.contains(3));
        Assertions.assertTrue(check.contains(4));
        Assertions.assertTrue(check.contains(5));
    }

    @Test
    void testPage() {
        List<Integer> set = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        List<Integer> check = ListUtils.page(set, 5, 0);
        Assertions.assertEquals(5, check.size());
        Assertions.assertTrue(check.contains(1));
        Assertions.assertTrue(check.contains(2));
        Assertions.assertTrue(check.contains(3));
        Assertions.assertTrue(check.contains(4));
        Assertions.assertTrue(check.contains(5));

        check = ListUtils.page(set, 5, 1);
        Assertions.assertEquals(5, check.size());
        Assertions.assertTrue(check.contains(6));
        Assertions.assertTrue(check.contains(7));
        Assertions.assertTrue(check.contains(8));
        Assertions.assertTrue(check.contains(9));
        Assertions.assertTrue(check.contains(10));

        check = ListUtils.page(set, 5, 2);
        Assertions.assertEquals(5, check.size());
        Assertions.assertTrue(check.contains(11));
        Assertions.assertTrue(check.contains(12));
        Assertions.assertTrue(check.contains(13));
        Assertions.assertTrue(check.contains(14));
        Assertions.assertTrue(check.contains(15));

        check = ListUtils.page(set, 5, 3);
        Assertions.assertEquals(4, check.size());
        Assertions.assertTrue(check.contains(16));
        Assertions.assertTrue(check.contains(17));
        Assertions.assertTrue(check.contains(18));
        Assertions.assertTrue(check.contains(19));
    }

    @Test
    void testPageEmpty() {
        List<Integer> set = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        List<Integer> check = ListUtils.page(set, 0, 0);
        Assertions.assertTrue(check.isEmpty());

        check = ListUtils.page(set, 5, 5);
        Assertions.assertTrue(check.isEmpty());

        check = ListUtils.page(set, 5, -1);
        Assertions.assertTrue(check.isEmpty());

        check = ListUtils.page(Collections.emptyList(), 5, 5);
        Assertions.assertTrue(check.isEmpty());
    }

    @Test
    void testIsEmpty() {
        Assertions.assertTrue(ListUtils.isEmpty(Collections.emptyList()));
        Assertions.assertTrue(ListUtils.isEmpty(null));
        Assertions.assertFalse(ListUtils.isEmpty(List.of(1, 2, 3, 4)));
    }

    @Test
    void testNotEmpty() {
        Assertions.assertFalse(ListUtils.notEmpty(Collections.emptyList()));
        Assertions.assertFalse(ListUtils.notEmpty(null));
        Assertions.assertTrue(ListUtils.notEmpty(List.of(1, 2, 3, 4)));
    }

    @Test
    void testSize() {
        Assertions.assertEquals(0, ListUtils.size(Collections.emptyList()));
        Assertions.assertEquals(0, ListUtils.size(null));
        Assertions.assertEquals(4, ListUtils.size(List.of(1, 2, 3, 4)));
    }

    @Test
    void testSafe() {
        Assertions.assertNotNull(ListUtils.safe(null));
        Assertions.assertNotNull(ListUtils.safe(List.of(1, 2, 3, 4)));
        Assertions.assertNotNull(ListUtils.safe(Collections.emptyList()));
    }

    @Test
    void testPages() {
        Assertions.assertEquals(0, ListUtils.pages(Collections.emptyList(), 5));
        Assertions.assertEquals(0, ListUtils.pages(null, 5));
        Assertions.assertEquals(
                4, ListUtils.pages(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19), 5));
    }

    @Test
    void testFilter() {
        List<Integer> check = ListUtils.filter(
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20),
                value -> value % 2 == 0);

        Assertions.assertEquals(10, check.size());
        Assertions.assertTrue(check.contains(2));
        Assertions.assertTrue(check.contains(4));
        Assertions.assertTrue(check.contains(6));
        Assertions.assertTrue(check.contains(8));
        Assertions.assertTrue(check.contains(10));
        Assertions.assertTrue(check.contains(12));
        Assertions.assertTrue(check.contains(14));
        Assertions.assertTrue(check.contains(16));
        Assertions.assertTrue(check.contains(18));
        Assertions.assertTrue(check.contains(20));
    }

    @Test
    void testSetValue() {
        List<TestValue> check = ListUtils.setValue(
                List.of(new TestValue(0), new TestValue(2), new TestValue(4), new TestValue(6)),
                100,
                TestValue::setValue);
        Assertions.assertEquals(4, check.size());
        Assertions.assertEquals(100, check.get(0).getValue());
        Assertions.assertEquals(100, check.get(1).getValue());
        Assertions.assertEquals(100, check.get(2).getValue());
        Assertions.assertEquals(100, check.get(3).getValue());
    }

    @Test
    void testGetValue() {
        List<? extends Integer> check = ListUtils.getValue(
                List.of(new TestValue(0), new TestValue(2), new TestValue(4), new TestValue(6)), TestValue::getValue);
        Assertions.assertEquals(4, check.size());
        Assertions.assertTrue(check.contains(0));
        Assertions.assertTrue(check.contains(2));
        Assertions.assertTrue(check.contains(4));
        Assertions.assertTrue(check.contains(6));
    }

    @Test
    void testToMap() {
        Map<Integer, TestValue> check = ListUtils.toMap(
                List.of(new TestValue(0), new TestValue(2), new TestValue(4), new TestValue(6)), TestValue::getValue);
        Assertions.assertEquals(4, check.size());
        Assertions.assertNotNull(check.get(0));
        Assertions.assertNotNull(check.get(2));
        Assertions.assertNotNull(check.get(4));
        Assertions.assertNotNull(check.get(6));
    }

    static class TestValue {
        Integer value;

        TestValue(Integer value) {
            this.value = value;
        }

        Integer getValue() {
            return value;
        }

        void setValue(Integer value) {
            this.value = value;
        }
    }
}
