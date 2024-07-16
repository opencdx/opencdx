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
import cdx.opencdx.client.service.OpenCDXAnalysisEngineClient;
import cdx.opencdx.grpc.service.iam.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Temperature Measurement gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXAnalysisEngineClientImpl implements OpenCDXAnalysisEngineClient {
    private static final String OPEN_CDX_ANALYSIS_ENGINE_MEASUREMENT_CLIENT_IMPL = "OpenCDXAnalysisEngineClientImpl";
    private final AnalysisEngineServiceGrpc.AnalysisEngineServiceBlockingStub analysisEngineServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param channel ManagedChannel for the gRPC Service invocations.
     */
    public OpenCDXAnalysisEngineClientImpl(ManagedChannel channel) {
        this.analysisEngineServiceBlockingStub = AnalysisEngineServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Temperature measurement client implementation.
     * @param analysisEngineServiceBlockingStub gRPC Blocking Stub for Provider.
     */
    public OpenCDXAnalysisEngineClientImpl(
            AnalysisEngineServiceGrpc.AnalysisEngineServiceBlockingStub analysisEngineServiceBlockingStub) {
        this.analysisEngineServiceBlockingStub = analysisEngineServiceBlockingStub;
    }

    /**
     * Method to gRPC Call temperature measurement createAnalysisEngine() api.
     *
     * @param request                CreateTemperatureMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public CreateAnalysisEngineResponse createAnalysisEngine(
            CreateAnalysisEngineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return analysisEngineServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createAnalysisEngine(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ANALYSIS_ENGINE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Temperature Service getAnalysisEngine() api.
     *
     * @param request                GetAnalysisEngineRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public GetAnalysisEngineResponse getAnalysisEngine(
            GetAnalysisEngineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return analysisEngineServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getAnalysisEngine(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ANALYSIS_ENGINE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Temperature Service updateTemperatureMeasurement() api.
     *
     * @param request                UpdateTemperatureMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UpdateAnalysisEngineResponse updateAnalysisEngine(
            UpdateAnalysisEngineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return analysisEngineServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateAnalysisEngine(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ANALYSIS_ENGINE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Temperature Service deleteTemperatureMeasurement() api.
     *
     * @param request                DeleteTemperatureMeasurementRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public DeleteAnalysisEngineResponse deleteAnalysisEngine(
            DeleteAnalysisEngineRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return analysisEngineServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteAnalysisEngine(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ANALYSIS_ENGINE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Temperature Service listTemperatureMeasurements() api.
     *
     * @param request                ListTemperatureMeasurementsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListAnalysisEnginesResponse listAnalysisEngines(
            ListAnalysisEnginesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return analysisEngineServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listAnalysisEngines(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_ANALYSIS_ENGINE_MEASUREMENT_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
