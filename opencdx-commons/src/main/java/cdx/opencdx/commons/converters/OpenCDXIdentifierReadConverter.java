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
import org.springframework.data.convert.ReadingConverter;

/**
 * Converter to read an OpenCDXIdentifier from an ObjectId.
 */
@ReadingConverter
public class OpenCDXIdentifierReadConverter implements Converter<OpenCDXIdentifier, ObjectId> {
    /**
     * Create a new OpenCDXIdentifierReadConverter.
     */
    public OpenCDXIdentifierReadConverter() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
    /**
     * Convert an OpenCDXIdentifier to an ObjectId.
     * @param source The OpenCDXIdentifier to convert.
     * @return The ObjectId.
     */
    @Override
    public ObjectId convert(OpenCDXIdentifier source) {
        return source.getObjectId();
    }
}
