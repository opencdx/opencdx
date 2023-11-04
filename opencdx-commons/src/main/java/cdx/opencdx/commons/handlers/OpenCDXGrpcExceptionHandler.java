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
package cdx.opencdx.commons.handlers;

import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.exceptions.OpenCDXException;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.exceptions.OpenCDXUnauthorized;
import io.grpc.Status;
import io.grpc.protobuf.StatusProto;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler;
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope;
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

/**
 * gRPC Exception Handler
 */
@Slf4j
@GRpcServiceAdvice
@ExcludeFromJacocoGeneratedReport
public class OpenCDXGrpcExceptionHandler {

    private static final String GRPC_EXCEPTION_HANDLER = "GRPC_EXCEPTION_HANDLER";

    /**
     * Default Constructor
     */
    public OpenCDXGrpcExceptionHandler() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Handler for AuthenticationCredentialsNotFoundException
     * @param cause AuthenticationCredentialsNotFoundException thrown
     * @param scope GRpcExceptionScope for processing
     * @return Status providing the google code and status information
     */
    @GRpcExceptionHandler
    public Status handleAuthenticationCredentialsNotFoundException(
            AuthenticationCredentialsNotFoundException cause, GRpcExceptionScope scope) {
        log.error("Response Exception in handleOpenCDXException: {} {}", cause.getMessage());
        return StatusProto.toStatusException(
                        new OpenCDXUnauthorized(GRPC_EXCEPTION_HANDLER, 3, cause.getMessage(), cause)
                                .getGrpcStatus(null))
                .getStatus();
    }
    /**
     * Handler for OpenCDXExceptions
     * @param cause OpenCDXEception thrown
     * @param scope GRpcExceptionScope for processing
     * @return Status providing the google code and status information
     */
    @GRpcExceptionHandler
    public Status handleOpenCDXException(OpenCDXException cause, GRpcExceptionScope scope) {
        log.error("Response Exception in handleOpenCDXException: {} {}", cause.getMessage());
        return StatusProto.toStatusException(cause.getGrpcStatus(null)).getStatus();
    }
    /**
     * Handler for Un-Caught Exceptions
     * @param cause Eception thrown
     * @param scope GRpcExceptionScope for processing
     * @return Status providing the Google code and status information
     */
    @GRpcExceptionHandler
    public Status handleOpenCDXClientException(OpenCDXClientException cause, GRpcExceptionScope scope) {
        log.error("Response Exception in handleOpenCDXClientException: {} {}", cause.getMessage());
        return StatusProto.toStatusException(new OpenCDXInternal(GRPC_EXCEPTION_HANDLER, 1, cause.getMessage(), cause)
                        .getGrpcStatus(cause.getDetailsList()))
                .getStatus();
    }
    /**
     * Handler for Un-Caught Exceptions
     * @param cause Eception thrown
     * @param scope GRpcExceptionScope for processing
     * @return Status providing the google code and status information
     */
    @GRpcExceptionHandler
    public Status handleException(Exception cause, GRpcExceptionScope scope) {
        log.error("Response Exception in handleException: {} {}", cause.getMessage());
        return StatusProto.toStatusException(
                        new OpenCDXInternal(GRPC_EXCEPTION_HANDLER, 2, "UnCaught Exception", cause).getGrpcStatus(null))
                .getStatus();
    }
}
