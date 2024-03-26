package cdx.opencdx.commons.converters;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@ReadingConverter
public class OpenCDXIdentifierReadConverter implements Converter<OpenCDXIdentifier, ObjectId> {
    @Override
    public ObjectId convert(OpenCDXIdentifier source) {
        return source.getObjectId();
    }
}
