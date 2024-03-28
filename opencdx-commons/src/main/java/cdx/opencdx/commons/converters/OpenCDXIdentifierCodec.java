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
        return new OpenCDXIdentifier(this.objectIdCodec.decode(bsonReader,decoderContext));
    }

    @Override
    public void encode(BsonWriter bsonWriter, OpenCDXIdentifier openCDXIdentifier, EncoderContext encoderContext) {
        this.objectIdCodec.encode(bsonWriter,openCDXIdentifier.getObjectId(),encoderContext);
    }

    @Override
    public Class<OpenCDXIdentifier> getEncoderClass() {
        return OpenCDXIdentifier.class;
    }
}
