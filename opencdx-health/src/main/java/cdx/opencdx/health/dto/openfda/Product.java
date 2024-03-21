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
package cdx.opencdx.health.dto.openfda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenFDA class
 */
@Slf4j
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("java:S116")
public class Product {
    private String product_number;
    private String reference_drug;
    private String brand_name;
    private List<ActiveIngredient> active_ingredients;
    private String reference_standard;
    private String dosage_form;
    private String route;
    private String marketing_status;
    private String te_code;

    /**
     * Default Constructor
     */
    public Product() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
}
