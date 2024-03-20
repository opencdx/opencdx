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
@SuppressWarnings("java:S116")
public class Result {
    private List<String> spl_product_data_elements;
    private List<String> indications_and_usage;
    private List<String> dosage_and_administration;
    private List<String> dosage_forms_and_strengths;
    private List<String> contraindications;
    private List<String> warnings_and_cautions;
    private List<String> adverse_reactions;
    private List<String> drug_interactions;
    private List<String> use_in_specific_populations;
    private List<String> pregnancy;
    private List<String> nursing_mothers;
    private List<String> pediatric_use;
    private List<String> geriatric_use;
    private List<String> drug_abuse_and_dependence;
    private List<String> controlled_substance;
    private List<String> abuse;
    private List<String> dependence;
    private List<String> overdosage;
    private List<String> description;
    private List<String> clinical_pharmacology;
    private List<String> mechanism_of_action;
    private List<String> pharmacodynamics;
    private List<String> pharmacokinetics;
    private List<String> nonclinical_toxicology;
    private List<String> carcinogenesis_and_mutagenesis_and_impairment_of_fertility;
    private List<String> clinical_studies;
    private List<String> how_supplied;
    private List<String> information_for_patients;
    private List<String> package_label_principal_display_panel;
    private String set_id;
    private String id;
    private String effective_time;
    private String version;
    private OpenFDA openfda;
    private List<Submission> submissions;
    private String application_number;
    private String sponsor_name;
    private List<Product> products;
}
