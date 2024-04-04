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
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

/**
 * Codec to read and write OpenCDXIdentifiers to and from BSON.
 */
public class OpenCDXIdentifierCodec implements Codec<OpenCDXIdentifier> {

    private final Codec<ObjectId> objectIdCodec;

    /**
     * Create a new OpenCDXIdentifierCodec with the given CodecRegistry.
     * @param codecRegistry The CodecRegistry to use.
     */
    public OpenCDXIdentifierCodec(CodecRegistry codecRegistry) {
        this.objectIdCodec = codecRegistry.get(ObjectId.class);
    }

    /**
     * Create a new OpenCDXIdentifierCodec with the given Codec.
     * @param bsonReader The BsonReader to use.
     * @param decoderContext The DecoderContext to use.
     * @return The OpenCDXIdentifier.
     */
    @Override
    public OpenCDXIdentifier decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new OpenCDXIdentifier(this.objectIdCodec.decode(bsonReader, decoderContext));
    }

    /**
     * Encode an OpenCDXIdentifier to BSON.
     * @param bsonWriter The BsonWriter to use.
     * @param openCDXIdentifier The OpenCDXIdentifier to encode.
     * @param encoderContext The EncoderContext to use.
     */
    @Override
    public void encode(BsonWriter bsonWriter, OpenCDXIdentifier openCDXIdentifier, EncoderContext encoderContext) {
        this.objectIdCodec.encode(bsonWriter, openCDXIdentifier.getObjectId(), encoderContext);
    }

    /**
     * Get the Class of the Open
     * @return The Class of the OpenCDXIdentifier.
     */
    @Override
    public Class<OpenCDXIdentifier> getEncoderClass() {
        return OpenCDXIdentifier.class;
    }
}
