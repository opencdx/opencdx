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
package cdx.opencdx.client.exceptions;

import com.google.protobuf.Any;
import com.google.rpc.Code;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenCDXClientException is the grpc client exceptions.
 *
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class OpenCDXClientException extends RuntimeException {

    /**
     * Signature for OpenCDx exceptions
     */
    private static final String OPENCDX_SIGNATURE = "OPENCDX-{}-{} - {}";

    /**
     * Google Code for gRPC assigned to this excepiton.
     */
    private final Code code;
    /**
     * Domain this exception was created in.
     */
    private final String domain;
    /**
     * Unique number to identify this exception in the domain.
     */
    private final int number;

    /**
     * Capture the details list from the client error.
     */
    private final List<Any> detailsList;

    /**
     * Constructor for Exception.
     * @param code Google Code this exception should return
     * @param domain Class Name exception created in
     * @param number Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     * @param detailsList List of detail information sent from client.
     * @param cause Throwable that was the cause of this exception.
     */
    public OpenCDXClientException(
            Code code, String domain, int number, String message, List<Any> detailsList, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.domain = domain;
        this.number = number;
        this.detailsList = detailsList;
        log.error(OPENCDX_SIGNATURE, this.domain, this.number, message, this);
    }

    @Override
    public String toString() {
        return String.format("OPENCDX-%s-%d - %s", this.domain, this.number, super.toString());
    }

    /**
     * The Google Code for this exception.
     * @return The Google Code for this exception.
     */
    public Code getCode() {
        return code;
    }
}
