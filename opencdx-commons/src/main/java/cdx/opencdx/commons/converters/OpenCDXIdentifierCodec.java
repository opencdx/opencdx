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

public class OpenCDXIdentifierCodec implements Codec<OpenCDXIdentifier> {

    private Codec<ObjectId> objectIdCodec;

    public OpenCDXIdentifierCodec(CodecRegistry codecRegistry) {
        this.objectIdCodec = codecRegistry.get(ObjectId.class);
    }

    @Override
    public OpenCDXIdentifier decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return new OpenCDXIdentifier(this.objectIdCodec.decode(bsonReader, decoderContext));
    }

    @Override
    public void encode(BsonWriter bsonWriter, OpenCDXIdentifier openCDXIdentifier, EncoderContext encoderContext) {
        this.objectIdCodec.encode(bsonWriter, openCDXIdentifier.getObjectId(), encoderContext);
    }

    @Override
    public Class<OpenCDXIdentifier> getEncoderClass() {
        return OpenCDXIdentifier.class;
    }
}
