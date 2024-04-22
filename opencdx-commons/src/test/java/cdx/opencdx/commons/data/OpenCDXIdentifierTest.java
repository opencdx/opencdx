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
package cdx.opencdx.commons.data;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;

class OpenCDXIdentifierTest {
    @Test
    void testGet() {
        OpenCDXIdentifier openCDXIdentifier = OpenCDXIdentifier.get();
        openCDXIdentifier.setId("5f7b1b3b7b3b7b3b7b3b7b3b");
        assertNotNull(openCDXIdentifier);
    }

    @Test
    void testDate() {
        OpenCDXIdentifier openCDXIdentifier = OpenCDXIdentifier.get();
        OpenCDXIdentifier openCDXIdentifier1 = OpenCDXIdentifier.get();
        OpenCDXIdentifier.getSmallestWithDate(Date.from(Instant.now()));
        OpenCDXIdentifier.isValid("5f7b1b3b7b3b7b3b7b3b7b3b");
        openCDXIdentifier.putToByteBuffer(ByteBuffer.allocate(14));
        openCDXIdentifier.toByteArray();
        openCDXIdentifier.hashCode();
        openCDXIdentifier.compareTo(openCDXIdentifier1);
        assertNotNull(openCDXIdentifier);
    }
}
