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
package health.safe.api.opencdx.communications;

import com.github.cloudyrock.spring.v5.EnableMongock;
import health.safe.api.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import java.util.List;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Application class for this microservice
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableMongock
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
@ComponentScan(basePackages = {"health.safe"})
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

/**
 * Rest Controller for Discovery Client
 */
@RestController
@ExcludeFromJacocoGeneratedReport
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * Discovery Client Rest Interface
     * @param applicationName Name of application
     * @return List of service instances.
     */
    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
}
