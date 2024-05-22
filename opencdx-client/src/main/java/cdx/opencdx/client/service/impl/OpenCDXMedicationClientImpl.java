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
import cdx.opencdx.client.service.OpenCDXMedicationClient;
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.service.health.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the Medication Administration gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXMedicationClientImpl implements OpenCDXMedicationClient {
    private static final String OPEN_CDX_MEDICATION_CLIENT_IMPL = "OpenCDXMedicationClientImpl";
    private final MedicationServiceGrpc.MedicationServiceBlockingStub medicationServiceBlockingStub;

    /**
     * Constructor for the Medication client implementation.
     * @param channel gRPC Blocking Stub for Provider.
     */
    public OpenCDXMedicationClientImpl(ManagedChannel channel) {
        this.medicationServiceBlockingStub = MedicationServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for the Medication client implementation.
     * @param medicationServiceBlockingStub gRPC Blocking Stub for Provider.
     */
    public OpenCDXMedicationClientImpl(
            MedicationServiceGrpc.MedicationServiceBlockingStub medicationServiceBlockingStub) {
        this.medicationServiceBlockingStub = medicationServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Medication prescribing() api.
     *
     * @param request                Medication to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Medication prescribing(Medication request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .prescribing(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICATION_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Medication Service listAllMedications() api.
     *
     * @param request                EndMedicationRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Medication ending(EndMedicationRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .ending(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICATION_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Medication Service listAllMedications() api.
     *
     * @param request                UpdateHeartRPMRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListMedicationsResponse listAllMedications(
            ListMedicationsRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listAllMedications(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICATION_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Medication Service listCurrentMedications() api.
     *
     * @param request                ListMedicationsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListMedicationsResponse listCurrentMedications(
            ListMedicationsRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listCurrentMedications(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICATION_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Medication Service searchMedications() api.
     *
     * @param request                SearchMedicationsRequest to pass
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ListMedicationsResponse searchMedications(
            SearchMedicationsRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .searchMedications(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICATION_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
