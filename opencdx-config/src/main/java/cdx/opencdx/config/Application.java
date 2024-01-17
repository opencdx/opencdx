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
package cdx.opencdx.config;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * The Application class is the entry point for running the Config Server application.
 * It is responsible for configuring and starting the Spring Boot application.
 *
 * This class uses the following annotations:
 * - @EnableConfigServer: This annotation enables the Config Server functionality.
 * - @SpringBootApplication: This annotation is a convenience annotation that combines @Configuration, @EnableAutoConfiguration, and @ComponentScan.
 *
 * Example usage:
 * SpringApplication.run(Application.class, args);
 */
@EnableConfigServer
@SpringBootApplication
@Generated
public class Application {

    /**
     * Default Constructor
     */
    public Application() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
    /**
     * The main method is the entry point for running the Config Server application.
     * It is responsible for configuring and starting the Spring Boot application.
     * This method uses the SpringApplication.run() method to run the application.
     *
     * @param args the command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
