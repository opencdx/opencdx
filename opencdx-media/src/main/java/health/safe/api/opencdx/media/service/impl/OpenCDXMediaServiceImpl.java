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
package health.safe.api.opencdx.media.service.impl;

import cdx.media.v2alpha.*;
import health.safe.api.opencdx.media.service.OpenCDXMediaService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
@Observed(name = "opencdx")
public class OpenCDXMediaServiceImpl implements OpenCDXMediaService {

    /**
     * Constructor taking the a PersonRepository
     */
    @Autowired
    public OpenCDXMediaServiceImpl() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Override
    public CreateMediaResponse createMedia(CreateMediaRequest request) {
        return CreateMediaResponse.getDefaultInstance();
    }

    @Override
    public ListMediaResponse listMedia(ListMediaRequest request) {
        return ListMediaResponse.getDefaultInstance();
    }

    @Override
    public GetMediaResponse getMedia(GetMediaRequest request) {
        return GetMediaResponse.getDefaultInstance();
    }

    @Override
    public UpdateMediaResponse updateMedia(UpdateMediaRequest request) {
        return UpdateMediaResponse.getDefaultInstance();
    }

    @Override
    public DeleteMediaResponse deleteMedia(DeleteMediaRequest request) {
        return DeleteMediaResponse.getDefaultInstance();
    }
}
