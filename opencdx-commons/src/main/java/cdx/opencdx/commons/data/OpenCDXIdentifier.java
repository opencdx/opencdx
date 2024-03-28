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

import java.nio.ByteBuffer;
import java.util.Date;
import org.bson.types.ObjectId;

public class OpenCDXIdentifier implements java.lang.Comparable<OpenCDXIdentifier>, java.io.Serializable {
    private final ObjectId id;

    public OpenCDXIdentifier() {
        this.id = new ObjectId();
    }

    public OpenCDXIdentifier(ObjectId id) {
        this.id = id;
    }

    public OpenCDXIdentifier(String hexString) {
        this.id = new ObjectId(hexString);
    }

    public static OpenCDXIdentifier get() {
        return new OpenCDXIdentifier(ObjectId.get());
    }

    public static OpenCDXIdentifier getSmallestWithDate(Date date) {
        return new OpenCDXIdentifier(ObjectId.getSmallestWithDate(date));
    }

    public static boolean isValid(String hexString) {
        return ObjectId.isValid(hexString);
    }

    public byte[] toByteArray() {
        return id.toByteArray();
    }

    public void putToByteBuffer(ByteBuffer buffer) {
        id.putToByteBuffer(buffer);
    }

    public int getTimestamp() {
        return id.getTimestamp();
    }

    public Date getDate() {
        return id.getDate();
    }

    public String toHexString() {
        return id.toHexString();
    }

    @Override
    public boolean equals(Object o) {
        return id.equals(o);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int compareTo(OpenCDXIdentifier other) {
        return id.compareTo(other.getObjectId());
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public ObjectId getObjectId() {
        return id;
    }

}
