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
package cdx.opencdx.commons.converters;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Converter to write an OpenCDXIdentifier to an ObjectId.
 */
@WritingConverter
public class OpenCDXIdentifierWriteConverter implements Converter<ObjectId, OpenCDXIdentifier> {
    /**
     * Create a new OpenCDXIdentifierWriteConverter.
     */
    public OpenCDXIdentifierWriteConverter() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Convert an ObjectId to an OpenCDXIdentifier.
     * @param source The ObjectId to convert.
     * @return The OpenCDXIdentifier.
     */
    @Override
    public OpenCDXIdentifier convert(ObjectId source) {
        return new OpenCDXIdentifier(source);
    }
}
