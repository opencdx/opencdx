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
package cdx.opencdx.connected.lab.config;

import cdx.opencdx.connected.lab.handler.OpenCDXLabConnected;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OpenCDXLabConnectionFactoryBean is a class to get the OpenCDXLab connection by identifier.
 */
@Slf4j
@Component
@ConfigurationProperties("opencdx")
public class OpenCDXLabConnectionFactoryBean {

    @Setter
    private Map<String, String> connections;

    private Map<String, Class<? extends OpenCDXLabConnected>> connectionsMap;

    /**
     * Default Constructor
     */
    public OpenCDXLabConnectionFactoryBean() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
    /**
     * Initialize the OpenCDXLabConnectionFactoryBean.
     * @throws Exception if the connection is not found
     */
    @PostConstruct
    public void initialize() throws Exception {
        log.info("Initializing OpenCDXLabConnectionFactoryBean");
        if (connections == null || connections.isEmpty()) {
            throw new IllegalStateException("No OpenCDXLab connections configured for active profile!");
        }

        Map<String, Class<? extends OpenCDXLabConnected>> connectionMap = new HashMap<>();
        for (Map.Entry<String, String> entry : connections.entrySet()) {
            Class<?> connectionClass = Class.forName(entry.getValue());
            if (!OpenCDXLabConnected.class.isAssignableFrom(connectionClass)) {
                throw new IllegalArgumentException("Invalid connection class: " + entry.getValue());
            }
            connectionMap.put(entry.getKey(), (Class<? extends OpenCDXLabConnected>) connectionClass);
        }
        // Store the validated connectionMap for later use
        this.connectionsMap = connectionMap;
        this.connectionsMap.keySet().forEach(key -> log.info("OpenCDXLab connection: {}", key));
    }

    /**
     * Get the OpenCDXLab connection by identifier.
     * @param identifier the connection identifier
     * @return the OpenCDXLabConnected
     * @throws Exception if the connection is not found
     */
    public OpenCDXLabConnected getConnection(String identifier) throws Exception {
        if (this.connectionsMap == null || !this.connectionsMap.containsKey(identifier)) {
            throw new IllegalArgumentException("Unknown OpenCDXLab connection identifier: " + identifier);
        }
        log.info("Getting OpenCDXLab connection: {}", identifier);
        Class<? extends OpenCDXLabConnected> connectionClass = this.connectionsMap.get(identifier);
        return connectionClass.getDeclaredConstructor().newInstance();
    }
}
