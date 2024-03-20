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
package cdx.opencdx.health.service.impl;

import cdx.opencdx.health.dto.openfda.Result;
import cdx.opencdx.health.dto.openfda.Search;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
import cdx.opencdx.health.service.OpenCDXApiFDA;
import cdx.opencdx.health.service.OpenCDXOpenFDAClient;
import io.micrometer.observation.annotation.Observed;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXApiFDAImpl implements OpenCDXApiFDA {

    private final OpenCDXOpenFDAClient openCDXOpenFDAClient;

    public OpenCDXApiFDAImpl(OpenCDXOpenFDAClient openCDXOpenFDAClient) {
        this.openCDXOpenFDAClient = openCDXOpenFDAClient;
    }

    @Override
    @SuppressWarnings({"java:S3776", "java:S2259"})
    public List<OpenCDXMedicationModel> getMedicationsByBrandName(String brandNamePrefix) {
        ResponseEntity<Search> drugs =
                this.openCDXOpenFDAClient.getDrugs("products.brand_name:\"" + brandNamePrefix + "\"", 1000, 0);

        List<Result> results = Collections.emptyList();

        if (drugs != null && drugs.getBody() != null) {
            results = drugs.getBody().getResults();
        }

        List<Result> medicationDrugs = results.stream()
                .map(result -> {
                    log.debug("Result: {}", result);

                    ResponseEntity<Search> label = this.openCDXOpenFDAClient.getLabel(
                            "openfda.application_number:\"" + result.getApplicationNumber() + "\"", 1000, 0);

                    if (label.getBody() != null
                            && label.getBody().getResults() != null
                            && !label.getBody().getResults().isEmpty()) {
                        Result drug = label.getBody().getResults().get(0);
                        drug.setProducts(result.getProducts());

                        return drug;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        List<OpenCDXMedicationModel> medications = new ArrayList<>();

        medicationDrugs.forEach(drug -> drug.getProducts().forEach(product -> {
            if (drug.getOpenfda() != null && drug.getOpenfda().getGenericName() != null) {
                for (String brandName : drug.getOpenfda().getBrandName()) {
                    if (brandName.equals(product.getBrandName())) {
                        OpenCDXMedicationModel medication = new OpenCDXMedicationModel(brandName, drug, product, false);
                        medications.add(medication);
                        log.debug("Medication: {}", medication);
                    }
                }
                for (String genericName : drug.getOpenfda().getGenericName()) {
                    if (genericName.equals(product.getBrandName())) {
                        OpenCDXMedicationModel medication =
                                new OpenCDXMedicationModel(genericName, drug, product, true);
                        medications.add(medication);
                        log.debug("Medication: {}", medication);
                    }
                }
            }
        }));
        return medications.stream()
                .distinct()
                .sorted(Comparator.comparing(OpenCDXMedicationModel::getMedicationName))
                .toList();
    }
}
