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
package cdx.opencdx.media.service.impl;

import cdx.opencdx.commons.collections.ListUtils;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.media.*;
import cdx.opencdx.grpc.types.MediaStatus;
import cdx.opencdx.media.model.OpenCDXMediaModel;
import cdx.opencdx.media.repository.OpenCDXMediaRepository;
import cdx.opencdx.media.service.OpenCDXMediaService;
import io.micrometer.observation.annotation.Observed;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service for processing Media Service Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXMediaServiceImpl implements OpenCDXMediaService {

    private static final String DOMAIN = "OpenCDXMediaServiceImpl";
    private static final String FAILED_TO_FIND_MEDIA = "Failed to find media: ";
    private final OpenCDXMediaRepository openCDXMediaRepository;

    /**
     * Constructor taking a OpenCDXMediaRepository
     * @param openCDXMediaRepository Repository for storing media records.
     */
    @Autowired
    public OpenCDXMediaServiceImpl(OpenCDXMediaRepository openCDXMediaRepository) {
        this.openCDXMediaRepository = openCDXMediaRepository;
    }

    @Override
    public CreateMediaResponse createMedia(CreateMediaRequest request) {
        OpenCDXMediaModel mediaModel = new OpenCDXMediaModel();
        mediaModel.setName(request.getName());
        mediaModel.setMediaType(request.getType());
        mediaModel.setShortDescription(request.getShortDescription());
        mediaModel.setDescription(request.getDescription());
        mediaModel.setLabels(new ArrayList<>(request.getLabelsList()));
        mediaModel.setStatus(MediaStatus.MEDIA_STATUS_UPLOADING);
        mediaModel = this.openCDXMediaRepository.save(mediaModel);

        return CreateMediaResponse.newBuilder()
                .setUploadUrl("/media/upload/" + mediaModel.getId().toHexString())
                .setMedia(mediaModel.getProtobufMessage())
                .build();
    }

    @Override
    public ListMediaResponse listMedia(ListMediaRequest request) {
        Page<OpenCDXMediaModel> all = this.openCDXMediaRepository.findAll(PageRequest.of(
                request.getPagination().getPageNumber(), request.getPagination().getPageSize()));

        return ListMediaResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllTemplates(
                        all.get().map(OpenCDXMediaModel::getProtobufMessage).toList())
                .build();
    }

    @Override
    public GetMediaResponse getMedia(GetMediaRequest request) {
        return GetMediaResponse.newBuilder()
                .setMedia(this.openCDXMediaRepository
                        .findById(new OpenCDXIdentifier(request.getId()))
                        .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, FAILED_TO_FIND_MEDIA + request.getId()))
                        .getProtobufMessage())
                .build();
    }

    @Override
    public UpdateMediaResponse updateMedia(UpdateMediaRequest request) {
        OpenCDXMediaModel mediaModel = this.openCDXMediaRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 2, FAILED_TO_FIND_MEDIA + request.getId()));

        mediaModel.setName(request.getName());
        mediaModel.setShortDescription(request.getShortDescription());
        mediaModel.setDescription(request.getDescription());
        mediaModel.setLabels(new ArrayList<>(ListUtils.safe(request.getLabelsList())));
        mediaModel.setMediaType(request.getType());

        return UpdateMediaResponse.newBuilder()
                .setMedia(this.openCDXMediaRepository.save(mediaModel).getProtobufMessage())
                .build();
    }

    @Override
    public DeleteMediaResponse deleteMedia(DeleteMediaRequest request) {
        OpenCDXMediaModel mediaModel = this.openCDXMediaRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 2, FAILED_TO_FIND_MEDIA + request.getId()));

        mediaModel.setStatus(MediaStatus.MEDIA_STATUS_DELETED);

        mediaModel = this.openCDXMediaRepository.save(mediaModel);
        log.info("Deleted Media: {}", request.getId());

        return DeleteMediaResponse.newBuilder()
                .setMedia(mediaModel.getProtobufMessage())
                .build();
    }
}
