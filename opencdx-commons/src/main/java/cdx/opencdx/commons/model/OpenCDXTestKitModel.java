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

import cdx.opencdx.grpc.data.TestKit;
import cdx.opencdx.grpc.types.SpecimenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenCDXTestKitModel
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OpenCDXTestKitModel {

    private String name;
    private String targetDisease;
    private SpecimenType specimenType;
    private String procedure;
    private OpenCDXInterpretationModel interpretationGuide;
    private String brand;
    private String approvalStatus;
    private Float accuracy;
    private Integer testDuration;
    private String testCaseId;

    /**
     * Constructor
     *
     * @param testKit TestKit
     */
    public OpenCDXTestKitModel(TestKit testKit) {
        this.name = testKit.getName();
        this.targetDisease = testKit.getTargetDisease();
        this.specimenType = testKit.getSpecimenType();
        this.procedure = testKit.getProcedure();
        if (testKit.hasInterpretationGuide()) {
            this.interpretationGuide = new OpenCDXInterpretationModel(testKit.getInterpretationGuide());
        }
        this.brand = testKit.getBrand();
        this.approvalStatus = testKit.getApprovalStatus();
        this.accuracy = testKit.getAccuracy();
        this.testDuration = testKit.getTestDuration();
        this.testCaseId = testKit.getTestCaseId();
    }

    /**
     * Get the Protobuf representation of this model
     *
     * @return TestKit
     */
    public TestKit getProtobuf() {
        TestKit.Builder builder = TestKit.newBuilder();

        if (this.name != null) {
            builder.setName(this.name);
        }
        if (this.targetDisease != null) {
            builder.setTargetDisease(this.targetDisease);
        }
        if (this.specimenType != null) {
            builder.setSpecimenType(this.specimenType);
        }
        if (this.procedure != null) {
            builder.setProcedure(this.procedure);
        }
        if (this.interpretationGuide != null) {
            builder.setInterpretationGuide(this.interpretationGuide.getProtobuf());
        }
        if (this.brand != null) {
            builder.setBrand(this.brand);
        }
        if (this.approvalStatus != null) {
            builder.setApprovalStatus(this.approvalStatus);
        }
        if (this.accuracy != null) {
            builder.setAccuracy(this.accuracy);
        }
        if (this.testDuration != null) {
            builder.setTestDuration(this.testDuration);
        }
        if (this.testCaseId != null) {
            builder.setTestCaseId(this.testCaseId);
        }

        return builder.build();
    }
}
