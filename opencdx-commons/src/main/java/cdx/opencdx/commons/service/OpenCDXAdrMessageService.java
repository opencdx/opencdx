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
package cdx.opencdx.commons.service;

import cdx.opencdx.grpc.data.ANFStatement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This service is for sending ANFS Statement messagees to the OpenCDX ADR.
 */
@FeignClient(name = "adrService")
public interface OpenCDXAdrMessageService {
    /**
     * This method sends an ANFS Statement message to the OpenCDX ADR.
     *
     * @param anfStatement The ANFS Statement message to be sent.
     * @return The response from the OpenCDX ADR.
     */
    @PostMapping(value = "/anf", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> postANFStatement(@RequestBody ANFStatement anfStatement);
}
