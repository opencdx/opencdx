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
package cdx.opencdx.commons.model;

import cdx.opencdx.grpc.data.Interpretation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * OpenCDXInterpretationModel
 */
@Slf4j
@Data
@RequiredArgsConstructor
public class OpenCDXInterpretationModel {

    private Map<String, String> positive;
    private Map<String, String> negative;

    /**
     * Constructor
     *
     * @param interpretation Interpretation
     */
    public OpenCDXInterpretationModel(Interpretation interpretation) {
        this.positive = interpretation.getPositiveMap();
        this.negative = interpretation.getNegativeMap();
    }

    /**
     * Get the Protobuf representation of this model
     *
     * @return Interpretation
     */
    public Interpretation getProtobuf() {
        Interpretation.Builder builder = Interpretation.newBuilder();
        if (this.positive != null) {
            builder.putAllPositive(this.positive);
        }
        if (this.negative != null) {
            builder.putAllNegative(this.negative);
        }
        return builder.build();
    }
}
