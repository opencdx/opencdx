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

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OpenFDA {
    private List<String> applicationNumber;
    private List<String> brandName;
    private List<String> genericName;
    private List<String> manufacturerName;
    private List<String> productNdc;
    private List<String> productType;
    private List<String> route;
    private List<String> substanceName;
    private List<String> rxcui;
    private List<String> splId;
    private List<String> splSetId;
    private List<String> packageNdc;
    private List<String> originalPackagerProductNdc;
    private List<String> upc;
    private List<String> unii;

    // Getters and Setters
}
