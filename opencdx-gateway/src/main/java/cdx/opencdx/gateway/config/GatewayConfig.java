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
package cdx.opencdx.gateway.config;

import cdx.opencdx.gateway.filter.ActuatorGlobalFilter;
import lombok.Generated;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GatewayConfig is a configuration class that sets up the gateway application.
 * It contains a method to create an instance of ActuatorGlobalFilter, which is
 * used to prevent access to the actuator endpoints.
 */
@Configuration
@Generated
public class GatewayConfig {
    /**
     * Default Constructor
     */
    public GatewayConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
    /**
     * This method creates an instance of the ActuatorGlobalFilter class, which is used as a global filter in the application gateway.
     * The ActuatorGlobalFilter class is responsible for preventing access to the actuator endpoints.
     *
     * @return an instance of the ActuatorGlobalFilter class
     */
    @Bean
    public GlobalFilter actuatorGlobalFilter() {
        return new ActuatorGlobalFilter();
    }
}
