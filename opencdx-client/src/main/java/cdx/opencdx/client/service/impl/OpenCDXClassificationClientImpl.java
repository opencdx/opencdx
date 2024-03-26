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
package cdx.opencdx.client.service.impl;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXClassificationClient;
import cdx.opencdx.grpc.neural.classification.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Classification gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXClassificationClientImpl implements OpenCDXClassificationClient {

    private final ClassificationServiceGrpc.ClassificationServiceBlockingStub classificationServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    @Generated
    public OpenCDXClassificationClientImpl(ManagedChannel channel) {
        this.classificationServiceBlockingStub = ClassificationServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Classification client implementation.
     * @param classificationServiceBlockingStub gRPC Blocking Stub for Classification.
     */
    public OpenCDXClassificationClientImpl(
            ClassificationServiceGrpc.ClassificationServiceBlockingStub classificationServiceBlockingStub) {
        this.classificationServiceBlockingStub = classificationServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Classification Service classify() api.
     *
     * @param request Classification request to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Classification Response.
     */
    @Override
    public ClassificationResponse classify(ClassificationRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return classificationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .classify(request);

        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    "OpenCDXClassificationClientImpl",
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service getRuleSets() api.
     *
     * @param request                Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public RuleSetsResponse getRuleSets(ClientRulesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return classificationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getRuleSets(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    "OpenCDXClassificationClientImpl",
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
