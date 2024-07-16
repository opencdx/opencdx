package cdx.opencdx.classification.config;

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.service.OpenCDXAnalysisEngine;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@ConfigurationProperties("opencdx")
public class OpenCDXClassificationEngineFactoryBean {

    @Setter
    private Map<String, String> engines;

    private final ApplicationContext applicationContext;

    /**
     * Default Constructor
     */
    public OpenCDXClassificationEngineFactoryBean(ApplicationContext applicationContext) {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
        this.applicationContext = applicationContext;
    }

    /**
     * Get the OpenCDXClassificationEngine by identifier.
     * @param identifier the engine identifier
     * @return the OpenCDXAnalysisEngine
     * @throws Exception if the engine is not found
     */
    public OpenCDXAnalysisEngine getEngine(String identifier) throws Exception {
        String beanName = engines.get(identifier);
        if(beanName == null) {
            beanName = engines.get("default");
        }
        log.warn("Engine: " + beanName);
        if (applicationContext.containsBean(beanName)) {
            return (OpenCDXAnalysisEngine) applicationContext.getBean(beanName);
        } else {
            throw new OpenCDXNotFound("OpenCDXClassificationEngineFactoryBean",1,"Engine not found: " + identifier );
        }
    }
}
