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
package cdx.opencdx.commons.handlers;

import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.dto.StatusMessage;
import cdx.opencdx.commons.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * Rest API Exception Handler
 */
@Slf4j
@ExcludeFromJacocoGeneratedReport
@ControllerAdvice
public class OpenCDXRestExceptionHandler {
    /**
     * Default Constructor
     */
    public OpenCDXRestExceptionHandler() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    private static final String DOMAIN = "GRPC_EXCEPTION_HANDLER";
    private static final String UN_CAUGHT_EXCEPTION = "UnCaught Exception";

    /**
     * Handles OpenCDXException based exceptions.
     * @param exception Exception thrown
     * @param request HTTP Request processing during this exception
     * @return ResponseEntity based on StatusMessage
     */
    @ExceptionHandler(OpenCDXException.class)
    public final ResponseEntity<StatusMessage> handleOpenCDXException(OpenCDXException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getRestStatus(request), exception.getStatus());
    }

    /**
     * Handles MethodArgumentNotValidExceptions and TypeMismatchExceptions
     * @param exception Exception thrown
     * @param request HTTP Request processing during this exception
     * @return ResponseEntity based on StatusMessage
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, TypeMismatchException.class})
    public final ResponseEntity<StatusMessage> handleBadRequestExceptions(Exception exception, WebRequest request) {
        OpenCDXException cdxException = new OpenCDXBadRequest(DOMAIN, 4, UN_CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(cdxException.getRestStatus(request), cdxException.getStatus());
    }
    /**
     * Handles HttpMediaTypeNotSupportedException
     * @param exception Exception thrown
     * @param request HTTP Request processing during this exception
     * @return ResponseEntity based on StatusMessage
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public final ResponseEntity<StatusMessage> handleHttpMediaTypeNotSupportedExceptions(
            HttpMediaTypeNotSupportedException exception, WebRequest request) {
        OpenCDXException cdxException = new OpenCDXUnsupportedMediaType(DOMAIN, 3, UN_CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(cdxException.getRestStatus(request), cdxException.getStatus());
    }

    /**
     * Handles HttpMediaTypeNotAcceptableException, MissingServletRequestPartException, ConversionNotSupportedException, ServletRequestBindingException,MissingServletRequestParameterException,
     * MissingPathVariableException, HttpRequestMethodNotSupportedException
     * @param exception Exception thrown
     * @param request HTTP Request processing during this exception
     * @return ResponseEntity based on StatusMessage
     */
    @ExceptionHandler({
        HttpMediaTypeNotAcceptableException.class,
        MissingServletRequestPartException.class,
        ConversionNotSupportedException.class,
        ServletRequestBindingException.class,
        MissingServletRequestParameterException.class,
        MissingPathVariableException.class,
        HttpRequestMethodNotSupportedException.class
    })
    public final ResponseEntity<StatusMessage> handleNotAcceptableExceptions(Exception exception, WebRequest request) {
        OpenCDXException cdxException = new OpenCDXNotAcceptable(DOMAIN, 2, UN_CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(cdxException.getRestStatus(request), cdxException.getStatus());
    }

    /**
     * Handles any OpenCDXClientException exception.
     * @param exception OpenCDXClientException thrown
     * @param request HTTP Request processing during this exception
     * @return ResponseEntity based on StatusMessage
     */
    @ExceptionHandler(OpenCDXClientException.class)
    public final ResponseEntity<StatusMessage> handleOpenCDXClientException(
            OpenCDXClientException exception, WebRequest request) {
        OpenCDXException cdxException = new OpenCDXInternal(DOMAIN, 1, exception.getMessage(), exception);
        return new ResponseEntity<>(cdxException.getRestStatus(request), cdxException.getStatus());
    }
    /**
     * Handles any uncaught exception.
     * @param exception Exception thrown
     * @param request HTTP Request processing during this exception
     * @return ResponseEntity based on StatusMessage
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<StatusMessage> handleAllException(Exception exception, WebRequest request) {
        OpenCDXException cdxException = new OpenCDXInternal(DOMAIN, 1, UN_CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(cdxException.getRestStatus(request), cdxException.getStatus());
    }
}
