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
package cdx.opencdx.health.service;

import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for the OpenCDXDoctorNotesService
 */
public interface OpenCDXDoctorNotesService {
    /**
     * Method to create DoctorNotes.
     * @param request CreateDoctorNotesRequest for DoctorNotes.
     * @return CreateDoctorNotesResponse with DoctorNotes.
     */
    CreateDoctorNotesResponse createDoctorNotes(CreateDoctorNotesRequest request);

    /**
     * Method to get DoctorNotes.
     * @param request GetDoctorNotesRequest for DoctorNotes.
     * @return GetDoctorNotesResponse with DoctorNotes.
     */
    GetDoctorNotesResponse getDoctorNotes(GetDoctorNotesRequest request);

    /**
     * Method to update DoctorNotes.
     * @param request UpdateDoctorNotesRequest for DoctorNotes.
     * @return UpdateDoctorNotesResponse with DoctorNotes.
     */
    UpdateDoctorNotesResponse updateDoctorNotes(UpdateDoctorNotesRequest request);

    /**
     * Method to delete DoctorNotes.
     * @param request DeleteDoctorNotesRequest for DoctorNotes.
     * @return SuccessResponse with DoctorNotes.
     */
    SuccessResponse deleteDoctorNotes(DeleteDoctorNotesRequest request);

    /**
     * Method to list DoctorNotes.
     * @param request ListDoctorNotesRequest for DoctorNotes.
     * @return ListDoctorNotesResponse with DoctorNotes.
     */
    ListDoctorNotesResponse listAllByPatientId(ListDoctorNotesRequest request);
}
