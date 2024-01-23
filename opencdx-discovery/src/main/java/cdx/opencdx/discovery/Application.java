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
package cdx.opencdx.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The Application class is the entry point for the application. It is responsible for bootstrapping the application
 * and starting the Eureka server.
 * <p>
 * The class is annotated with @EnableEurekaServer, which enables the Eureka server functionality. It is also annotated
 * with @SpringBootApplication, which is a convenience annotation that combines @Configuration, @EnableAutoConfiguration,
 * and @ComponentScan annotations. This annotation scans the classpath for other Spring components, configurations,
 * and services, and starts the server.
 * <p>
 * The main() method is the starting point of the application. It calls SpringApplication.run() method passing the
 * Application class and the command-line arguments. This method uses Spring Boot to bootstrap and launch the application.
 */
@EnableEurekaServer
@SpringBootApplication
public class Application {
    /**
     * Default Constructor
     */
    public Application() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
    /**
     * The main method is the entry point for the application.
     * It starts the Spring Boot application by calling SpringApplication.run()
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
