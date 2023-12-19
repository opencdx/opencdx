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
package cdx.opencdx.commons.exceptions;

import cdx.opencdx.commons.collections.ListUtils;
import cdx.opencdx.commons.dto.StatusMessage;
import com.google.protobuf.Any;
import com.google.rpc.Code;
import com.google.rpc.DebugInfo;
import com.google.rpc.ErrorInfo;
import com.google.rpc.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

/**
 * OpenCDXException is the abstraction to any OpenCDX related exceptions.
 *
 */
@Slf4j
public abstract class OpenCDXException extends RuntimeException {

    /**
     * Signature for OpenCDx exceptions
     */
    private static final String OPENCDX_SIGNATURE = "OPENCDX-{}-{} - {}";

    /**
     * Status for the exception
     */
    @Getter
    private final HttpStatus status;

    /**
     * Code for the exception
     */
    @Getter
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
     * MetaData that can be passed in this exception for any relevant data.
     */
    @Getter
    @Setter
    @SuppressWarnings("java:S1165")
    private Map<String, String> metaData;

    /**
     * Constructor for Exception.
     * @param status HttpStatus this exception should return
     * @param code Google Code this exception should return
     * @param domain Class Name exception created in
     * @param number Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     */
    protected OpenCDXException(HttpStatus status, Code code, String domain, int number, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.domain = domain;
        this.number = number;
        log.error(OPENCDX_SIGNATURE, this.domain, this.number, message, this);
    }

    /**
     * Constructor for Exception.
     * @param status HttpStatus this exception should return
     * @param code Google Code this exception should return
     * @param domain Class Name exception created in
     * @param number Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     * @param cause Throwable that was the cause of this exception.
     */
    protected OpenCDXException(
            HttpStatus status, Code code, String domain, int number, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
        this.domain = domain;
        this.number = number;
        log.error(OPENCDX_SIGNATURE, this.domain, this.number, message, this);
    }

    /**
     * Constructor for Exception.
     * @param status HttpStatus this exception should return
     * @param code Google Code this exception should return
     * @param domain Class Name exception created in
     * @param number Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     * @param cause Throwable that was the cause of this exception.
     * @param enableSuppression Boolean to indicate allow suppression.
     * @param writableStackTrace Boolean to indicate writeable stack trace.
     */
    @SuppressWarnings("java:S107")
    protected OpenCDXException(
            HttpStatus status,
            Code code,
            String domain,
            int number,
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
        this.code = code;
        this.domain = domain;
        this.number = number;
        log.error(OPENCDX_SIGNATURE, this.domain, this.number, message, this);
    }

    @Override
    public String toString() {
        return String.format("OPENCDX-%s-%d - %s", this.domain, this.number, super.toString());
    }

    /**
     * Status for the gRPC Exception Handler
     * @param detailsList List of details to add
     * @return Status for the gRPC Exception Handler
     */
    public Status getGrpcStatus(List<Any> detailsList) {
        log.info("Getting gRPC Status for Error Handling");

        List<Any> details = new ArrayList<>();
        if (ListUtils.notEmpty(detailsList)) {
            details.addAll(detailsList);
        }
        ErrorInfo.Builder errorInfo = ErrorInfo.newBuilder().setDomain(this.domain);
        if (this.getMessage() != null) {
            errorInfo.setReason(this.getMessage());
        } else {
            errorInfo.setDomain("No Reason available");
        }
        if (this.metaData != null) {
            errorInfo.putAllMetadata(this.metaData);
        }
        details.add(Any.pack(errorInfo.build()));

        DebugInfo.Builder debugInfo =
                DebugInfo.newBuilder().setDetail(String.format("OPENCDX-%s-%d", this.domain, this.number));
        for (StackTraceElement stackTraceElement : this.getStackTrace()) {
            debugInfo.addStackEntries(stackTraceElement.toString());
        }
        details.add(Any.pack(debugInfo.build()));

        Throwable cause = this.getCause();
        while (cause != null) {
            debugInfo = DebugInfo.newBuilder();
            if (cause.getMessage() != null) {
                debugInfo.setDetail(cause.getMessage());
            }
            for (StackTraceElement stackTraceElement : cause.getStackTrace()) {
                debugInfo.addStackEntries(stackTraceElement.toString());
            }
            details.add(Any.pack(debugInfo.build()));

            cause = cause.getCause();
        }

        Status.Builder statusBuilder =
                Status.newBuilder().setCode(this.code.getNumber()).addAllDetails(details);
        if (this.getMessage() != null) {
            statusBuilder.setMessage(this.getMessage());
        } else {
            statusBuilder.setMessage("No Message Given");
        }
        return statusBuilder.build();
    }

    /**
     * Returns the StatusMessage based on the data contained in this exception
     * @param request WebRequest for getting the original request.
     * @return StatusMessage for the Rest Exception Handler.
     */
    public StatusMessage getRestStatus(WebRequest request) {
        log.info("Getting HTTP Status for Error Handling");
        List<String> errorMessages = new ArrayList<>();

        errorMessages.add(this.getMessage());
        Throwable cause = this.getCause();
        while (cause != null) {
            if (cause.getMessage() != null) {
                errorMessages.add(cause.getMessage());
            }
            cause = cause.getCause();
        }

        return StatusMessage.builder()
                .cause(this)
                .path(request.getDescription(false))
                .errorMessages(errorMessages)
                .metadata(this.getMetaData())
                .parameters(request.getParameterMap())
                .errorCode(this.status.toString())
                .businessMessage(this.getMessage())
                .build();
    }
}
