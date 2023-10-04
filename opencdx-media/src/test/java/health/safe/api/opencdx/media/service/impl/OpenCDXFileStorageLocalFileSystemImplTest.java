/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package health.safe.api.opencdx.media.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import health.safe.api.opencdx.media.config.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class OpenCDXFileStorageLocalFileSystemImplTest {

    @Test
    void constructor() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withPropertyValues(
                        "media.upload-dir=./src/test/java/health/safe/api/opencdx/media/service/impl/OpenCDXFileStorageLocalFileSystemImplTest.java")
                .withUserConfiguration(AppConfig.class);

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> contextRunner.run(context -> {
                    OpenCDXFileStorageLocalFileSystemImpl serivce =
                            context.getBean(OpenCDXFileStorageLocalFileSystemImpl.class);
                }));
    }
}
