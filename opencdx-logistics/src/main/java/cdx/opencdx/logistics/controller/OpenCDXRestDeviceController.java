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
package cdx.opencdx.logistics.controller;

import cdx.opencdx.grpc.data.Device;
import cdx.opencdx.grpc.service.logistics.DeleteResponse;
import cdx.opencdx.grpc.service.logistics.DeviceIdRequest;
import cdx.opencdx.grpc.service.logistics.DeviceListRequest;
import cdx.opencdx.grpc.service.logistics.DeviceListResponse;
import cdx.opencdx.logistics.service.OpenCDXDeviceService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the Device
 */
@Slf4j
@RestController
@RequestMapping(value = "/device", produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestDeviceController {

    private final OpenCDXDeviceService openCDXDeviceService;

    /**
     * Constructor with OpenCDXDeviceService for processing
     * @param openCDXDeviceService OpenCDXDeviceService for processing requests.
     */
    @Autowired
    public OpenCDXRestDeviceController(OpenCDXDeviceService openCDXDeviceService) {
        this.openCDXDeviceService = openCDXDeviceService;
    }

    /**
     * Method to get a DeviceById
     *
     * @param id id of the DeviceIdRequest to retrieve.
     * @return The requested Device.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") String id) {
        return new ResponseEntity<>(
                this.openCDXDeviceService.getDeviceById(
                        DeviceIdRequest.newBuilder().setDeviceId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Method to add a device
     *
     * @param device Device to add
     * @return The added Device.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Device> addDevice(@RequestBody Device device) {
        return new ResponseEntity<>(this.openCDXDeviceService.addDevice(device), HttpStatus.OK);
    }

    /**
     * Method to update a device
     *
     * @param device Device to update.
     * @return The updated Device.
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Device> updateDevice(@RequestBody Device device) {
        return new ResponseEntity<>(this.openCDXDeviceService.updateDevice(device), HttpStatus.OK);
    }

    /**
     * Method to delete a country.
     * @param id Id of the country to delete.
     * @return Response with the deleted country.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteDevice(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXDeviceService.deleteDevice(
                        DeviceIdRequest.newBuilder().setDeviceId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List Devices
     *
     * @param deviceListRequest request for Devices.
     * @return the requested connected tests
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceListResponse> listDevices(@RequestBody DeviceListRequest deviceListRequest) {
        return new ResponseEntity<>(this.openCDXDeviceService.listDevices(deviceListRequest), HttpStatus.OK);
    }
}
