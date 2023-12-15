/*
 * Copyright 2023 Safe Health Systems, Inc.
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
import cdx.opencdx.grpc.media.*;

/**
 * Client Interface to the gRPC Media service
 */
public interface OpenCDXMediaClient {
    /**
     * Method to create media and be returned instructions on where to upload.
     * @param request CreateMediaRequest with the media information for creating the request.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return CreateMediaResponse providing where to upload the file.
     */
    CreateMediaResponse createMedia(CreateMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Request to delete Media
     * @param request DeleteMediaRequest with ID of media to delete.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return DeleteMediaResponse indicating if deleted.
     */
    DeleteMediaResponse deleteMedia(DeleteMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Method to retrieve information on a specific media.
     * @param request GetMediaRequest to retrieve.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return GetMediaResponse for the requested media
     */
    GetMediaResponse getMedia(GetMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Method to Update Media
     * @param request Updated Information for the Media
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return UpdateMediaResponse with the updated media information.
     */
    UpdateMediaResponse updateMedia(UpdateMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials);
    /**
     * Method to list media, with pagination.
     * @param request ListMediaRequest to list media.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return ListMediaResponse with pagination.
     */
    ListMediaResponse listMedia(ListMediaRequest request, OpenCDXCallCredentials openCDXCallCredentials);
}
