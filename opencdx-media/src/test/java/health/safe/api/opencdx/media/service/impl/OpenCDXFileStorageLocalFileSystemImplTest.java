package health.safe.api.opencdx.media.service.impl;

import health.safe.api.opencdx.client.config.ClientConfig;
import health.safe.api.opencdx.client.service.OpenCDXHelloworldClient;
import health.safe.api.opencdx.commons.exceptions.OpenCDXInternalServerError;
import health.safe.api.opencdx.media.config.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXFileStorageLocalFileSystemImplTest {

    @Test
    void constructor() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withPropertyValues("media.upload-dir=./src/test/java/health/safe/api/opencdx/media/service/impl/OpenCDXFileStorageLocalFileSystemImplTest.java")
                .withUserConfiguration(AppConfig.class);

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> contextRunner.run(context -> {
                    OpenCDXFileStorageLocalFileSystemImpl serivce = context.getBean(OpenCDXFileStorageLocalFileSystemImpl.class);
                }));
    }
}