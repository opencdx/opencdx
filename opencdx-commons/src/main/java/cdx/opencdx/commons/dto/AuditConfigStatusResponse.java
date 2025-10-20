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
package cdx.opencdx.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response object for audit configuration status
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditConfigStatusResponse {
    /**
     * Whether NATS audit publishing is enabled
     */
    private boolean natsAuditEnabled;
    
    /**
     * Whether the configuration flag was explicitly set
     * true = flag was found in configuration
     * false = using default value
     */
    private boolean configEntryFound;
    
    /**
     * The source of the configuration value
     * Values: "environment", "application.yml", "default"
     */
    private String configSource;
}

