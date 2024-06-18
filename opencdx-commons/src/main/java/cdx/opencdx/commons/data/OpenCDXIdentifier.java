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
import lombok.Setter;
import org.bson.types.ObjectId;

/**
 * OpenCDXIdentifier is a wrapper around ObjectId that provides a more descriptive name and implements Comparable.
 */
@Setter
public class OpenCDXIdentifier implements java.lang.Comparable<OpenCDXIdentifier>, java.io.Serializable {
    /**
     * The ObjectId for this OpenCDXIdentifier.
     */
    private ObjectId id;

    /**
     * Set the ObjectId for this OpenCDXIdentifier from a hex string.
     * @param hexString The hex string to set.
     */
    public void setId(String hexString) {
        this.id = new ObjectId(hexString);
    }

    /**
     * Create a new OpenCDXIdentifier with a new ObjectId.
     */
    public OpenCDXIdentifier() {
        this.id = new ObjectId();
    }

    /**
     * Create a new OpenCDXIdentifier with the given ObjectId.
     * @param id The ObjectId to use.
     */
    public OpenCDXIdentifier(ObjectId id) {
        this.id = id;
    }

    /**
     * Create a new OpenCDXIdentifier with the given hex string.
     * @param hexString The hex string to use.
     */
    public OpenCDXIdentifier(String hexString) {
        this.id = new ObjectId(hexString);
    }

    /**
     * Create a new OpenCDXIdentifier with the given timestamp and counter.
     *
     * @return The OpenCDXIdentifier.
     */
    public static OpenCDXIdentifier get() {
        return new OpenCDXIdentifier(ObjectId.get());
    }
    /**
     * Get the smallest OpenCDXIdentifier with the given date.
     * @param date The date to use.
     * @return The OpenCDXIdentifier.
     */
    public static OpenCDXIdentifier getSmallestWithDate(Date date) {
        return new OpenCDXIdentifier(ObjectId.getSmallestWithDate(date));
    }
    /**
     * Check if the given string is a valid hex string.
     * @param hexString The string to check.
     * @return True if the string is a valid hex string.
     */
    public static boolean isValid(String hexString) {
        return ObjectId.isValid(hexString);
    }
    /**
     * Get the byte array representation of this OpenCDXIdentifier.
     * @return The byte array.
     */
    public byte[] toByteArray() {
        return id.toByteArray();
    }

    /**
     * Set this OpenCDXIdentifier from a byte array.
     * @param buffer The byte array to set.
     */
    public void putToByteBuffer(ByteBuffer buffer) {
        id.putToByteBuffer(buffer);
    }
    /**
     * Get the timestamp of this OpenCDXIdentifier.
     * @return The timestamp.
     */
    public int getTimestamp() {
        return id.getTimestamp();
    }
    /**
     * Get the date of this OpenCDXIdentifier.
     * @return The date.
     */
    public Date getDate() {
        return id.getDate();
    }
    /**
     * Get the hex string of this OpenCDXIdentifier.
     * @return The counter.
     */
    public String toHexString() {
        return id.toHexString();
    }
    /**
     * Determines if the objects are equal.
     * @param o The counter to set.
     * @return Boolean indicating if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        return id.equals(o);
    }

    /**
     * Get the hash code of this OpenCDXIdentifier.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Compare this OpenCDXIdentifier to another.
     * @param other The other OpenCDXIdentifier to compare to.
     * @return The comparison result.
     */
    public int compareTo(OpenCDXIdentifier other) {
        return id.compareTo(other.getObjectId());
    }
    /**
     * Get the string representation of this OpenCDXIdentifier.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return id.toString();
    }
    /**
     * Get the ObjectId of this OpenCDXIdentifier.
     * @return The ObjectId.
     */
    public ObjectId getObjectId() {
        return id;
    }
}
