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
import cdx.opencdx.client.service.OpenCDXMedicationAdministrationClient;
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.data.MedicationAdministration;
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
public class OpenCDXMedicationAdministrationClientImpl implements OpenCDXMedicationAdministrationClient {
    private static final String OPEN_CDX_MEDICATION_ADMINISTRATION_CLIENT_IMPL =
            "OpenCDXMedicationAdministrationClientImpl";
    private final MedicationAdministrationServiceGrpc.MedicationAdministrationServiceBlockingStub
            medicationAdministrationServiceBlockingStub;

    /**
     * Constructor for the Medication Administration client implementation.
     * @param channel gRPC Blocking Stub for Provider.
     */
    public OpenCDXMedicationAdministrationClientImpl(ManagedChannel channel) {
        this.medicationAdministrationServiceBlockingStub = MedicationAdministrationServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for the Medication Administration measurement client implementation.
     * @param medicationAdministrationServiceBlockingStub gRPC Blocking Stub for Provider.
     */
    public OpenCDXMedicationAdministrationClientImpl(
            MedicationAdministrationServiceGrpc.MedicationAdministrationServiceBlockingStub
                    medicationAdministrationServiceBlockingStub) {
        this.medicationAdministrationServiceBlockingStub = medicationAdministrationServiceBlockingStub;
    }

    /**
     * Method to track when medication is given to a patient.
     *
     * @param request                Request for the medication administration.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the medication administration.
     */
    @Override
    public MedicationAdministration trackMedicationAdministration(
            MedicationAdministration request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicationAdministrationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .trackMedicationAdministration(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICATION_ADMINISTRATION_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to get medication information by ID.
     *
     * @param request                Request for the medication by ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the medication.
     */
    @Override
    public Medication getMedicationById(
            GetMedicationByIdRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicationAdministrationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getMedicationById(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICATION_ADMINISTRATION_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to get medication information by patient ID within a date range.
     *
     * @param request                Request for the medication by patient ID.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Response with the list of medication.
     */
    @Override
    public ListMedicationsResponse listMedications(
            ListMedicationsRequest request, OpenCDXCallCredentials openCDXCallCredentials) {
        try {
            return medicationAdministrationServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .listMedications(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_MEDICATION_ADMINISTRATION_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
