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
import cdx.opencdx.client.service.OpenCDXVaccineClient;
import cdx.opencdx.grpc.data.Vaccine;
import cdx.opencdx.grpc.service.health.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Vaccine gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXVaccineClientImpl implements OpenCDXVaccineClient {
    private static final String OPEN_CDX_VACCINE_CLIENT_IMPL = "OpenCDXVaccineClientImpl";
    private final VaccineServiceGrpc.VaccineServiceBlockingStub vaccineServiceBlockingStub;

    /**
     * Constructor for the Vaccine client implementation.
     * @param channel gRPC Blocking Stub for Vaccine.
     */
    public OpenCDXVaccineClientImpl(ManagedChannel channel) {
        this.vaccineServiceBlockingStub = VaccineServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for the Vaccine client implementation.
     * @param vaccineServiceBlockingStub gRPC Blocking Stub for Vaccine.
     */
    public OpenCDXVaccineClientImpl(VaccineServiceGrpc.VaccineServiceBlockingStub vaccineServiceBlockingStub) {
        this.vaccineServiceBlockingStub = vaccineServiceBlockingStub;
    }

    /**
     * Method to track when vaccine is given to a patient.
     *
     * @param request                Request for the vaccine administration.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the vaccine administration.
     */
    @Override
    public Vaccine trackVaccineAdministration(Vaccine request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return vaccineServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .trackVaccineAdministration(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_VACCINE_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to get vaccine information by ID.
     *
     * @param request                Request for the medication by ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the vaccine.
     */
    @Override
    public Vaccine getVaccineById(GetVaccineByIdRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return vaccineServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getVaccineById(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_VACCINE_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to get vaccine information by patient ID within a date range.
     *
     * @param request                Request for the medication by patient ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the list of medication.
     */
    @Override
    public ListVaccinesResponse listVaccines(
            ListVaccinesRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return vaccineServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listVaccines(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_VACCINE_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
