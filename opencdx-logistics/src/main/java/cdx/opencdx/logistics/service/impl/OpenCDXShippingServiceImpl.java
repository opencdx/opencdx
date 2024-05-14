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
package cdx.opencdx.logistics.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.AuditEntity;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.logistics.*;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.grpc.types.ShippingStatus;
import cdx.opencdx.logistics.model.OpenCDXOrderModel;
import cdx.opencdx.logistics.repository.OpenCDXOrderRepository;
import cdx.opencdx.logistics.service.OpenCDXShippingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXShippingServiceImpl implements OpenCDXShippingService {

    private static final String DOMAIN = "OpenCDXShippingServiceImpl";
    private static final String OBJECT = "OBJECT";
    private static final String ORDER = "Order: ";
    private static final String FAILED_TO_FIND_PROFILE = "Failed to find Profile: ";
    private static final String FAILED_TO_CONVERT_ORDER = "Failed to convert Order";
    private static final String FAILED_TO_FIND_ORDER = "Failed to find Order: ";
    private static final String ACCESSING_ORDER = "Accessing Order";
    private final OpenCDXOrderRepository openCDXOrderRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXProfileRepository openCDXProfileRepository;

    /**
     * Default constructor
     *
     * @param openCDXOrderRepository repository for order data
     * @param openCDXCurrentUser current user service
     * @param openCDXAuditService audit service
     * @param objectMapper object mapper
     * @param openCDXProfileRepository profile repository
     */
    public OpenCDXShippingServiceImpl(
            OpenCDXOrderRepository openCDXOrderRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXProfileRepository openCDXProfileRepository) {
        this.openCDXOrderRepository = openCDXOrderRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXProfileRepository = openCDXProfileRepository;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        log.info("Creating Order");
        OpenCDXProfileModel patient = this.openCDXProfileRepository
                .findById(new OpenCDXIdentifier(request.getOrder().getPatientId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 1, FAILED_TO_FIND_PROFILE + request.getOrder().getPatientId()));

        OpenCDXOrderModel model = this.openCDXOrderRepository.save(new OpenCDXOrderModel(request.getOrder()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating Order",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(patient.getId().toHexString())
                            .setNationalHealthId(patient.getNationalHealthId())
                            .build(),
                    ORDER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_ORDER, e);

            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return CreateOrderResponse.newBuilder().setOrder(model.getProtobuf()).build();
    }

    @Override
    public GetOrderResponse getOrder(GetOrderRequest request) {
        log.info("Getting Order");
        OpenCDXOrderModel model = this.openCDXOrderRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_ORDER + request.getId()));

        OpenCDXProfileModel patient = this.openCDXProfileRepository
                .findById(model.getPatientId())
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 4, FAILED_TO_FIND_PROFILE + model.getPatientId().toHexString()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    ACCESSING_ORDER,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(patient.getId().toHexString())
                            .setNationalHealthId(patient.getNationalHealthId())
                            .build(),
                    ORDER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(DOMAIN, 5, FAILED_TO_CONVERT_ORDER, e);

            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return GetOrderResponse.newBuilder().setOrder(model.getProtobuf()).build();
    }

    @Override
    public UpdateOrderResponse updateOrder(UpdateOrderRequest request) {
        log.info("Updating Order");
        OpenCDXOrderModel model = this.openCDXOrderRepository
                .findById(new OpenCDXIdentifier(request.getOrder().getId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 6, FAILED_TO_FIND_ORDER + request.getOrder().getId()));

        final String patientId = model.getPatientId().toHexString();
        OpenCDXProfileModel patient = this.openCDXProfileRepository
                .findById(model.getPatientId())
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 7, FAILED_TO_FIND_PROFILE + patientId));

        model = this.openCDXOrderRepository.save(model.update(request.getOrder()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    ACCESSING_ORDER,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(patient.getId().toHexString())
                            .setNationalHealthId(patient.getNationalHealthId())
                            .build(),
                    ORDER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(DOMAIN, 8, FAILED_TO_CONVERT_ORDER, e);

            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateOrderResponse.newBuilder().setOrder(model.getProtobuf()).build();
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest request) {
        log.info("Canceling Order");
        OpenCDXOrderModel model = this.openCDXOrderRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 9, FAILED_TO_FIND_ORDER + request.getId()));

        String patientId = model.getPatientId().toHexString();

        OpenCDXProfileModel patient = this.openCDXProfileRepository
                .findById(model.getPatientId())
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 10, FAILED_TO_FIND_PROFILE + patientId));
        model.setStatus(ShippingStatus.CANCELED);

        model = this.openCDXOrderRepository.save(model);

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    ACCESSING_ORDER,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(patient.getId().toHexString())
                            .setNationalHealthId(patient.getNationalHealthId())
                            .build(),
                    ORDER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 11, FAILED_TO_CONVERT_ORDER, e);

            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return CancelOrderResponse.newBuilder().setOrder(model.getProtobuf()).build();
    }

    @Override
    public ListOrdersResponse listOrders(ListOrdersRequest request) {
        log.info("Listing Orders");
        Pageable pageable;
        if (request.getPagination().hasSort()) {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize(),
                    request.getPagination().getSortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                    request.getPagination().getSort());
        } else {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize());
        }
        Page<OpenCDXOrderModel> all;
        if (request.hasPatientId()) {
            all = this.openCDXOrderRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        } else {
            all = this.openCDXOrderRepository.findAll(pageable);
        }

        all.forEach(order -> {
            OpenCDXProfileModel patient = this.openCDXProfileRepository
                    .findById(order.getPatientId())
                    .orElseThrow(() -> new OpenCDXNotFound(
                            DOMAIN,
                            12,
                            FAILED_TO_FIND_PROFILE + order.getPatientId().toHexString()));
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        ACCESSING_ORDER,
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        AuditEntity.newBuilder()
                                .setPatientId(patient.getId().toHexString())
                                .setNationalHealthId(patient.getNationalHealthId())
                                .build(),
                        ORDER + order.getId().toHexString(),
                        this.objectMapper.writeValueAsString(order));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 13, FAILED_TO_CONVERT_ORDER, e);

                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
                throw openCDXNotAcceptable;
            }
        });

        return ListOrdersResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllOrders(all.get().map(OpenCDXOrderModel::getProtobuf).toList())
                .build();
    }
}
