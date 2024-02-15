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
package cdx.opencdx.connected.test.controller;

import cdx.opencdx.connected.test.service.OpenCDXCountryService;
import cdx.opencdx.grpc.common.Country;
import cdx.opencdx.grpc.inventory.*;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

/**
 * GRPC Country Controller
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx")
public class OpenCDXGrpcCountryController extends CountryServiceGrpc.CountryServiceImplBase {

    private final OpenCDXCountryService openCDXCountryService;

    /**
     * Constructor with OpenCDXCountryService for processing
     * @param openCDXCountryService OpenCDXCountryService for processing requests.
     */
    public OpenCDXGrpcCountryController(OpenCDXCountryService openCDXCountryService) {
        this.openCDXCountryService = openCDXCountryService;
    }

    @Secured({})
    @Override
    public void getCountryById(CountryIdRequest request, StreamObserver<Country> responseObserver) {
        responseObserver.onNext(this.openCDXCountryService.getCountryById(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void addCountry(Country request, StreamObserver<Country> responseObserver) {
        responseObserver.onNext(this.openCDXCountryService.addCountry(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void updateCountry(Country request, StreamObserver<Country> responseObserver) {
        responseObserver.onNext(this.openCDXCountryService.updateCountry(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void deleteCountry(CountryIdRequest request, StreamObserver<DeleteResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCountryService.deleteCountry(request));
        responseObserver.onCompleted();
    }

    @Secured({})
    @Override
    public void listCountries(CountryListRequest request, StreamObserver<CountryListResponse> responseObserver) {
        responseObserver.onNext(this.openCDXCountryService.listCountries(request));
        responseObserver.onCompleted();
    }
}
