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
package cdx.opencdx.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The Application class is the entry point for the Spring Boot application.
 * It is responsible for starting up the application and initializing the necessary components.
 * <p>
 * This class is annotated with the following annotations:
 * - @EnableAdminServer: Enables the Admin Server functionality in the application.
 * - @EnableDiscoveryClient: Enables the registration of this application with a discovery server.
 * - @SpringBootApplication: Indicates that this is a Spring Boot application and enables auto-configuration.
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class Application {
    /**
     * Default constructor for the Application class.
     * This constructor allows creating an instance of Application without providing any initial values.
     */
    public Application() {
        // Default constructor body (usually empty for a Spring Boot application)
    }

    /**
     * The main method is the entry point for the Spring Boot application.
     * It starts up the application and initializes the necessary components.
     *
     * @param args The command line arguments passed to the application.
     * @see Application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
