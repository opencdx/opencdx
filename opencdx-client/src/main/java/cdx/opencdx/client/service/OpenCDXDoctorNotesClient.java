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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for communicating with the DoctorNotes service.
 */
public interface OpenCDXDoctorNotesClient {
    /**
     * Method to create DoctorNotes.
     * @param request CreateDoctorNotesRequest for doctor notes.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return CreateDoctorNotesResponse with doctor notes.
     */
    CreateDoctorNotesResponse createDoctorNotes(
            CreateDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to get DoctorNotes.
     * @param request GetDoctorNotesRequest for doctor notes.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return GetDoctorNotesResponse with doctor notes.
     */
    GetDoctorNotesResponse getDoctorNotes(GetDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to update DoctorNotes.
     * @param request UpdateDoctorNotesRequest for doctor notes.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return UpdateDoctorNotesResponse with doctor notes.
     */
    UpdateDoctorNotesResponse updateDoctorNotes(
            UpdateDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to delete DoctorNotes.
     * @param request DeleteDoctorNotesRequest for doctor notes.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return SuccessResponse with doctor notes.
     */
    SuccessResponse deleteDoctorNotes(DeleteDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to list  DoctorNotes.
     * @param request ListDoctorNotesRequest for doctor notes.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return ListDoctorNotesResponse with doctor notes.
     */
    ListDoctorNotesResponse listAllByPatientId(
            ListDoctorNotesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
