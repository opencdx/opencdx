package cdx.opencdx.commons.converters;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
public class OpenCDXIdentifierWriteConverter implements Converter<ObjectId, OpenCDXIdentifier> {
    @Override
    public OpenCDXIdentifier convert(ObjectId source) {
        return new OpenCDXIdentifier(source);
    }
}
