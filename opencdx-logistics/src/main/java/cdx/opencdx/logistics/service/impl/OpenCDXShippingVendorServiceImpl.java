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
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.grpc.common.EmailAddress;
import cdx.opencdx.grpc.common.EmailType;
import cdx.opencdx.grpc.common.PhoneNumber;
import cdx.opencdx.grpc.common.PhoneType;
import cdx.opencdx.grpc.communication.Notification;
import cdx.opencdx.grpc.shipping.*;
import cdx.opencdx.logistics.dto.OpenCDXShippingRequest;
import cdx.opencdx.logistics.dto.OpenCDXShippingResponse;
import cdx.opencdx.logistics.model.OpenCDXShippingModel;
import cdx.opencdx.logistics.repository.OpenCDXShippingRepository;
import cdx.opencdx.logistics.service.OpenCDXShippingVendor;
import cdx.opencdx.logistics.service.OpenCDXShippingVendorService;
import com.google.protobuf.Timestamp;
import io.micrometer.observation.annotation.Observed;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * OpenCDX shipping vendor service implementation
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXShippingVendorServiceImpl implements OpenCDXShippingVendorService {

    private final Map<String, OpenCDXShippingVendor> vendors;

    private final OpenCDXShippingRepository openCDXShippingRepository;
    private final OpenCDXCommunicationService openCDXCommunicationService;
    private final OpenCDXProfileRepository openCDXProfileRepository;

    /**
     * Instantiates a new OpenCDXShippingVendorServiceImpl.
     * @param openCDXShippingRepository the openCDX shipping repository
     * @param openCDXCommunicationService the openCDX communication service
     * @param openCDXProfileRepository the openCDX profile repository
     */
    public OpenCDXShippingVendorServiceImpl(
            OpenCDXShippingRepository openCDXShippingRepository,
            OpenCDXCommunicationService openCDXCommunicationService,
            OpenCDXProfileRepository openCDXProfileRepository) {
        this.openCDXShippingRepository = openCDXShippingRepository;
        this.openCDXCommunicationService = openCDXCommunicationService;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.vendors = new HashMap<>();

        OpenCDXShippingVendor vendor = new UpsShippingVendor();
        this.vendors.put(vendor.getVendorId(), vendor);
        vendor = new UspsShippingVendor();
        this.vendors.put(vendor.getVendorId(), vendor);
        vendor = new FedexShippingVendor();
        this.vendors.put(vendor.getVendorId(), vendor);
        vendor = new DoorDashShippingVendor();
        this.vendors.put(vendor.getVendorId(), vendor);

        this.vendors.keySet().forEach(key -> log.info("Vendor: {}", key));
    }

    @Override
    public ShippingVendorResponse getShippingVendors(ShippingRequest request) {
        log.info("Getting Shipping Vendor Options");
        OpenCDXShippingRequest openCDXShippingRequest = new OpenCDXShippingRequest(request);
        List<OpenCDXShippingModel> models = new ArrayList<>();

        for (OpenCDXShippingVendor vendor : vendors.values()) {
            List<OpenCDXShippingModel> shippingVendors = vendor.getShippingVendors(openCDXShippingRequest);
            if (shippingVendors != null && !shippingVendors.isEmpty()) {
                models.addAll(shippingVendors);
            }
        }

        return ShippingVendorResponse.newBuilder()
                .addAllOptions(
                        models.stream().map(OpenCDXShippingModel::toProtobuf).toList())
                .build();
    }

    @Override
    public ShippingResponse shipPackage(Shipping request) {
        log.info("Shipping Package");
        OpenCDXShippingModel openCDXShippingModel = new OpenCDXShippingModel(request);

        OpenCDXShippingResponse openCDXShippingResponse =
                this.vendors.get(request.getShippingVendorId()).shipPackage(openCDXShippingModel);

        openCDXShippingModel.update(openCDXShippingResponse);

        this.openCDXShippingRepository.save(openCDXShippingModel);

        DeliveryTracking.Builder builder = DeliveryTracking.newBuilder();
        builder.setTrackingId(openCDXShippingResponse.getTrackingNumber());
        builder.setOrderId(request.getPackageDetails().getId());
        builder.setStatus(openCDXShippingResponse.getStatus());
        builder.setStartDatetime(Timestamp.newBuilder()
                .setSeconds(Instant.now().getEpochSecond())
                .build());
        builder.setAssignedCourier(request.getShippingVendorId());

        return openCDXShippingResponse.toProtobuf();
    }

    @Override
    public DeliveryTrackingResponse createDeliveryTracking(DeliveryTrackingRequest request) {
        if (request.getDeliveryTracking().hasTrackingId()) {
            OpenCDXShippingModel openCDXShippingModel = this.openCDXShippingRepository
                    .findByTrackingNumber(request.getDeliveryTracking().getTrackingId())
                    .orElseThrow(
                            () -> new OpenCDXNotFound("OpenCDXShippingVendorServiceImpl", 1, "Failed to find patient"));
            String patientId = openCDXShippingModel.getPackageDetails().getPatientId();
            OpenCDXProfileModel patient = this.openCDXProfileRepository
                    .findById(new OpenCDXIdentifier(patientId))
                    .orElseThrow(
                            () -> new OpenCDXNotFound("OpenCDXShippingVendorServiceImpl", 2, "Failed to find patient"));

            Notification.Builder builder = Notification.newBuilder()
                    .setEventId(OpenCDXCommunicationService.CREATE_SHIPMENT)
                    .putAllVariables(getCreateShipmentVariables(patient, openCDXShippingModel));
            builder.setPatientId(patient.getId().toHexString());

            EmailAddress emailAddress = patient.getPrimaryContactInfo().getEmailsList().stream()
                    .filter(email -> email.getType().equals(EmailType.EMAIL_TYPE_PERSONAL))
                    .findFirst()
                    .orElse(patient.getPrimaryContactInfo().getEmailsList().stream()
                            .filter(email -> email.getType().equals(EmailType.EMAIL_TYPE_WORK))
                            .findFirst()
                            .orElse(patient.getPrimaryContactInfo().getEmailsList().stream()
                                    .findFirst()
                                    .orElse(null)));
            if (emailAddress != null) {
                builder.addAllToEmail(List.of(emailAddress.getEmail()));
            }
            List<String> mobileList = patient.getPrimaryContactInfo().getPhoneNumbersList().stream()
                    .filter(phoneNumber -> phoneNumber.getType().equals(PhoneType.PHONE_TYPE_MOBILE))
                    .map(PhoneNumber::getNumber)
                    .toList();
            if (!mobileList.isEmpty()) {
                builder.addAllToPhoneNumber(mobileList);
            }

            this.openCDXCommunicationService.sendNotification(builder.build());
        }
        return DeliveryTrackingResponse.newBuilder()
                .setDeliveryTracking(request.getDeliveryTracking())
                .build();
    }

    private static Map<String, String> getCreateShipmentVariables(OpenCDXProfileModel patient, OpenCDXShippingModel openCDXShippingModel) {
        Map<String, String> map = new HashMap<>();
        map.put("firstName", patient.getFullName().getFirstName());
        map.put("lastName", patient.getFullName().getLastName());
        map.put("notification", "Updated delivery tracking ");
        map.put("trackingNumber", openCDXShippingModel.getTrackingNumber());
        map.put("itemShipped", openCDXShippingModel.getPackageDetails().getTestCaseId());
        map.put("address1", patient.getAddresses().getFirst().getAddress1());
        map.put("address2", patient.getAddresses().getFirst().getAddress2());
        map.put("address3", patient.getAddresses().getFirst().getAddress3());
        map.put("city", patient.getAddresses().getFirst().getCity());
        map.put("postalCode", patient.getAddresses().getFirst().getPostalCode());
        map.put("state", patient.getAddresses().getFirst().getState());
        map.put("countryId", patient.getAddresses().getFirst().getCountryId());
        return map;
    }

    /**
     * Retrieve delivery tracking information by ID from the provided DeliveryTrackingRequest.
     * @param request The DeliveryTrackingRequest containing the ID for retrieval
     * @return Message containing details of the requested delivery tracking
     */
    @Override
    public DeliveryTrackingResponse getDeliveryTracking(DeliveryTrackingRequest request) {

        return DeliveryTrackingResponse.newBuilder()
                .setDeliveryTracking(request.getDeliveryTracking())
                .build();
    }
}
