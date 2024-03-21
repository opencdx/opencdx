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
import feign.FeignException;
import io.micrometer.observation.annotation.Observed;
import java.util.*;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXApiFDAImpl implements OpenCDXApiFDA {

    private final String openFDAApiKey;

    public static final String MEDICATION = "Medication: {}";
    private final OpenCDXOpenFDAClient openCDXOpenFDAClient;

    public OpenCDXApiFDAImpl(OpenCDXOpenFDAClient openCDXOpenFDAClient, @Value("${openFda.apiKey}") String openFDAApiKey) {
        this.openCDXOpenFDAClient = openCDXOpenFDAClient;
        if(openFDAApiKey != null && !openFDAApiKey.isEmpty()) {
            this.openFDAApiKey = openFDAApiKey;
        } else {
            this.openFDAApiKey = null;
        }
    }

    @Override
    @SuppressWarnings({"java:S3776", "java:S2259"})
    public List<OpenCDXMedicationModel> getMedicationsByBrandName(String brandNamePrefix) {
        ResponseEntity<Search> drugs;
        try {
            if(this.openFDAApiKey != null) {
                drugs = this.openCDXOpenFDAClient.getDrugs("products.brand_name:\"" + brandNamePrefix + "\"", 1000, 0);
            } else {
                drugs = this.openCDXOpenFDAClient.getDrugs(openFDAApiKey,"products.brand_name:\"" + brandNamePrefix + "\"", 1000, 0);
            }
        } catch (FeignException e) {
            log.warn("Failed fetching drugs for brand name: {}", brandNamePrefix);
            return Collections.emptyList();
        }
        List<Result> results = Collections.emptyList();

        if (drugs != null && drugs.getBody() != null) {
            results = drugs.getBody().getResults();
        }

        List<Result> medicationDrugs = results.stream()
                .map(result -> {
                    try {
                        if(result.getOpenfda() != null && result.getOpenfda().getProduct_ndc() != null) {
                            ResponseEntity<Search> label;

                            if (this.openFDAApiKey != null) {
                                label = this.openCDXOpenFDAClient.getLabel(
                                        "openfda.product_ndc:\"" + result.getOpenfda().getProduct_ndc() + "\"", 1000, 0);
                            } else {
                                label = this.openCDXOpenFDAClient.getLabel(openFDAApiKey,
                                        "openfda.product_ndc:\"" + result.getOpenfda().getProduct_ndc() + "\"", 1000, 0);
                            }

                            if (label.getBody() != null
                                    && label.getBody().getResults() != null
                                    && !label.getBody().getResults().isEmpty()) {
                                Result drug = label.getBody().getResults().get(0);
                                drug.setProducts(result.getProducts());

                                return drug;
                            }
                        }
                    } catch (FeignException e) {
                        log.warn("Failed fetching label for application number: {}", result.getApplication_number());
                    }

                    return result;
                })
                .toList();

        List<OpenCDXMedicationModel> medications = new ArrayList<>();

        medicationDrugs.forEach(drug -> drug.getProducts().forEach(product -> {
            if (product.getMarketing_status() != null
                    && !product.getMarketing_status().equalsIgnoreCase("Discontinued")) {
                OpenCDXMedicationModel medication =
                        new OpenCDXMedicationModel(product.getBrand_name(), drug, product, false);
                medications.add(medication);
                log.info(MEDICATION, medication);

                if (drug.getOpenfda() != null && drug.getOpenfda().getGeneric_name() != null) {
                    for (String genericName : drug.getOpenfda().getGeneric_name()) {
                        medication = new OpenCDXMedicationModel(genericName, drug, product, true);
                        medications.add(medication);
                        log.info(MEDICATION, medication);
                    }
                }
            }
        }));

        medications.forEach(medication -> log.info(MEDICATION, medication));

        List<OpenCDXMedicationModel> list = medications.stream()
                .distinct()
                .sorted(Comparator.comparing(OpenCDXMedicationModel::getMedicationName))
                .toList();
        list.forEach(medication -> log.info("Filtered Medication: {}", medication));

        return list;
    }
}
