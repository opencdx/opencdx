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
package cdx.opencdx.connected.test;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Application class for this microservice
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"cdx.opencdx"})
@EnableMongoRepositories(basePackages = {"cdx.opencdx"})
@ExcludeFromJacocoGeneratedReport
public class Application {
    /**
     * Default Constructor
     */
    public Application() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Main Method for application
     * @param args Arguments for application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
