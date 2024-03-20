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
public class Result {
    private List<String> splProductDataElements;
    private List<String> indicationsAndUsage;
    private List<String> dosageAndAdministration;
    private List<String> dosageFormsAndStrengths;
    private List<String> contraindications;
    private List<String> warningsAndCautions;
    private List<String> adverseReactions;
    private List<String> drugInteractions;
    private List<String> useInSpecificPopulations;
    private List<String> pregnancy;
    private List<String> nursingMothers;
    private List<String> pediatricUse;
    private List<String> geriatricUse;
    private List<String> drugAbuseAndDependence;
    private List<String> controlledSubstance;
    private List<String> abuse;
    private List<String> dependence;
    private List<String> overdosage;
    private List<String> description;
    private List<String> clinicalPharmacology;
    private List<String> mechanismOfAction;
    private List<String> pharmacodynamics;
    private List<String> pharmacokinetics;
    private List<String> nonclinicalToxicology;
    private List<String> carcinogenesisAndMutagenesisAndImpairmentOfFertility;
    private List<String> clinicalStudies;
    private List<String> howSupplied;
    private List<String> informationForPatients;
    private List<String> packageLabelPrincipalDisplayPanel;
    private String setId;
    private String id;
    private String effectiveTime;
    private String version;
    private OpenFDA openfda;

    private List<Submission> submissions;
    private String applicationNumber;
    private String sponsorName;
    private List<Product> products;
}
