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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.junit.jupiter.api.Test;

class OpenCDXIdentifierCodecTest {

    @Test
    void openCDXIdentifierCodec() {
        CodecRegistry codecRegistry = mock(CodecRegistry.class);
        BsonReader bsonReader = mock(BsonReader.class);
        DecoderContext decoderContext = mock(DecoderContext.class);
        EncoderContext encoderContext = mock(EncoderContext.class);
        BsonWriter bsonWriter = mock(BsonWriter.class);
        OpenCDXIdentifier openCDXIdentifier = new OpenCDXIdentifier();
        Codec objectIdCodec = mock(Codec.class);

        when(codecRegistry.get(any())).thenReturn(objectIdCodec);
        OpenCDXIdentifierCodec openCDXIdentifierCodec = new OpenCDXIdentifierCodec(codecRegistry);
        openCDXIdentifierCodec.encode(bsonWriter, openCDXIdentifier, encoderContext);
        openCDXIdentifierCodec.decode(bsonReader, decoderContext);
        openCDXIdentifierCodec.getEncoderClass();
        assertNotNull(openCDXIdentifierCodec);
    }
}
