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
package health.safe.api.opencdx.commons.handlers;

import health.safe.api.opencdx.client.exceptions.OpenCDXClientException;
import health.safe.api.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import health.safe.api.opencdx.commons.exceptions.OpenCDXException;
import health.safe.api.opencdx.commons.exceptions.OpenCDXInternal;
import io.grpc.Status;
import io.grpc.protobuf.StatusProto;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler;
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope;
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice;

/**
 * gRPC Exception Handler
 */
@Slf4j
@GRpcServiceAdvice
@ExcludeFromJacocoGeneratedReport
public class OpenCDXGrpcExceptionHandler {
    /**
     * Default Constructor
     */
    public OpenCDXGrpcExceptionHandler() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
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
        return StatusProto.toStatusException(new OpenCDXInternal("GRPC_EXCEPTION_HANDLER", 1, cause.getMessage(), cause)
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
                        new OpenCDXInternal("GRPC_EXCEPTION_HANDLER", 1, "UnCaught Exception", cause)
                                .getGrpcStatus(null))
                .getStatus();
    }
}
