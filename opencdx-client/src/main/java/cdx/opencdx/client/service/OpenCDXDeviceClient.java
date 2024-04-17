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
import cdx.opencdx.grpc.data.Device;
import cdx.opencdx.grpc.service.logistics.DeleteResponse;
import cdx.opencdx.grpc.service.logistics.DeviceIdRequest;
import cdx.opencdx.grpc.service.logistics.DeviceListRequest;
import cdx.opencdx.grpc.service.logistics.DeviceListResponse;

/**
 * Interface for communicating with the Logistics Device microservice.
 */
public interface OpenCDXDeviceClient {
    /**
     * Method to gRPC Call Device Service getDeviceById() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    Device getDeviceById(DeviceIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Device Service addDevice() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    Device addDevice(Device request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException;

    /**
     * Method to gRPC Call Device Service updateDevice() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    Device updateDevice(Device request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException;

    /**
     * Method to gRPC Call Device Service deleteDevice() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    DeleteResponse deleteDevice(DeviceIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Device Service listTestCase() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    DeviceListResponse listDevices(DeviceListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
