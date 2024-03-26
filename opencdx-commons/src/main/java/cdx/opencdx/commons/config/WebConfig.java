package cdx.opencdx.commons.config;

import cdx.opencdx.commons.converters.OpenCDXIdentifierReadConverter;
import cdx.opencdx.commons.converters.OpenCDXIdentifierWriteConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("Adding converters");
        registry.addConverter(new OpenCDXIdentifierReadConverter());
        registry.addConverter(new OpenCDXIdentifierWriteConverter());
    }
}
