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

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXDeviceClient;
import cdx.opencdx.grpc.data.Device;
import cdx.opencdx.grpc.service.logistics.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenCDXDeviceClientImplTest {

    @Mock
    DeviceServiceGrpc.DeviceServiceBlockingStub deviceServiceBlockingStub;

    OpenCDXDeviceClient openCDXDeviceClient;

    @BeforeEach
    void setUp() {
        this.deviceServiceBlockingStub = Mockito.mock(DeviceServiceGrpc.DeviceServiceBlockingStub.class);
        this.openCDXDeviceClient = new OpenCDXDeviceClientImpl(this.deviceServiceBlockingStub);
        Mockito.when(deviceServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.deviceServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.deviceServiceBlockingStub);
    }

    @Test
    void getDeviceById() {
        Mockito.when(this.deviceServiceBlockingStub.getDeviceById(Mockito.any(DeviceIdRequest.class)))
                .thenReturn(Device.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Device.getDefaultInstance(),
                this.openCDXDeviceClient.getDeviceById(DeviceIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getDeviceByIdException() {
        Mockito.when(this.deviceServiceBlockingStub.getDeviceById(Mockito.any(DeviceIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeviceIdRequest request = DeviceIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDeviceClient.getDeviceById(request, openCDXCallCredentials));
    }

    @Test
    void addDevice() {
        Mockito.when(this.deviceServiceBlockingStub.addDevice(Mockito.any(Device.class)))
                .thenReturn(Device.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Device.getDefaultInstance(),
                this.openCDXDeviceClient.addDevice(Device.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void addDeviceException() {
        Mockito.when(this.deviceServiceBlockingStub.addDevice(Mockito.any(Device.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Device request = Device.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDeviceClient.addDevice(request, openCDXCallCredentials));
    }

    @Test
    void updateDevice() {
        Mockito.when(this.deviceServiceBlockingStub.updateDevice(Mockito.any(Device.class)))
                .thenReturn(Device.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                Device.getDefaultInstance(),
                this.openCDXDeviceClient.updateDevice(Device.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateDeviceException() {
        Mockito.when(this.deviceServiceBlockingStub.updateDevice(Mockito.any(Device.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        Device request = Device.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDeviceClient.updateDevice(request, openCDXCallCredentials));
    }

    @Test
    void deleteDevice() {
        Mockito.when(this.deviceServiceBlockingStub.deleteDevice(Mockito.any(DeviceIdRequest.class)))
                .thenReturn(DeleteResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteResponse.getDefaultInstance(),
                this.openCDXDeviceClient.deleteDevice(DeviceIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteDeviceException() {
        Mockito.when(this.deviceServiceBlockingStub.deleteDevice(Mockito.any(DeviceIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeviceIdRequest request = DeviceIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDeviceClient.deleteDevice(request, openCDXCallCredentials));
    }

    @Test
    void listTestCase() {
        Mockito.when(this.deviceServiceBlockingStub.listDevices(Mockito.any(DeviceListRequest.class)))
                .thenReturn(DeviceListResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeviceListResponse.getDefaultInstance(),
                this.openCDXDeviceClient.listDevices(DeviceListRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listTestCaseException() {
        Mockito.when(this.deviceServiceBlockingStub.listDevices(Mockito.any(DeviceListRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeviceListRequest request = DeviceListRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXDeviceClient.listDevices(request, openCDXCallCredentials));
    }
}
