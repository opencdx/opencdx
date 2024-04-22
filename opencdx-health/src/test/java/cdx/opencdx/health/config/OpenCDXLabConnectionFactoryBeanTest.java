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
package cdx.opencdx.health.config;

import cdx.opencdx.health.handler.OpenCDXLabConnected;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class OpenCDXLabConnectionFactoryBeanTest {
    OpenCDXLabConnectionFactoryBean openCDXLabConnectionFactoryBean;

    @Test
    void test() {
        openCDXLabConnectionFactoryBean = new OpenCDXLabConnectionFactoryBean();
        ReflectionTestUtils.setField(openCDXLabConnectionFactoryBean, "connections", null);
        Assertions.assertThrows(IllegalStateException.class, () -> openCDXLabConnectionFactoryBean.initialize());
    }

    @Test
    void testEmpty() {
        Map<String, String> map = new HashMap<>();
        openCDXLabConnectionFactoryBean = new OpenCDXLabConnectionFactoryBean();
        ReflectionTestUtils.setField(openCDXLabConnectionFactoryBean, "connections", map);
        Assertions.assertThrows(IllegalStateException.class, () -> openCDXLabConnectionFactoryBean.initialize());
    }

    @Test
    void test2() {
        openCDXLabConnectionFactoryBean = new OpenCDXLabConnectionFactoryBean();
        Map<String, String> connectionMap = new HashMap<>();
        connectionMap.put("key", "java.lang.String");
        ReflectionTestUtils.setField(openCDXLabConnectionFactoryBean, "connections", connectionMap);
        Assertions.assertThrows(IllegalArgumentException.class, () -> openCDXLabConnectionFactoryBean.initialize());
    }

    @Test
    void test3() {
        openCDXLabConnectionFactoryBean = new OpenCDXLabConnectionFactoryBean();
        Map<String, Class<? extends OpenCDXLabConnected>> connectionMap = new HashMap<>();
        connectionMap.put("someIdentifier", OpenCDXLabConnected.class);
        ReflectionTestUtils.setField(openCDXLabConnectionFactoryBean, "connectionsMap", null);
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> openCDXLabConnectionFactoryBean.getConnection("identify"));
    }

    @Test
    void test4() {
        openCDXLabConnectionFactoryBean = new OpenCDXLabConnectionFactoryBean();
        Map<String, Class<? extends OpenCDXLabConnected>> connectionMap = new HashMap<>();
        connectionMap.put("identifier", OpenCDXLabConnected.class);
        ReflectionTestUtils.setField(openCDXLabConnectionFactoryBean, "connectionsMap", connectionMap);
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> openCDXLabConnectionFactoryBean.getConnection("identify"));
    }
}
