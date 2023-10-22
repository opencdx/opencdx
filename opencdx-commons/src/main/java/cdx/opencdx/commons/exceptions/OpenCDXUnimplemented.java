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

import com.google.rpc.Code;
import org.springframework.http.HttpStatus;

/**
 * OpenCDXUnimplemented Exception occurs when current operation is not implemented.
 * <p>
 *  Https Status: NOT_IMPLEMENTED
 *  <p>
 *  Google Code: UNIMPLEMENTED
 */
public class OpenCDXUnimplemented extends OpenCDXException {
    /**
     * Constructor for Exception.
     * @param domain Class Name exception created in
     * @param number Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     */
    public OpenCDXUnimplemented(String domain, int number, String message) {
        super(HttpStatus.NOT_IMPLEMENTED, Code.UNIMPLEMENTED, domain, number, message);
    }

    /**
     * Constructor for Exception.
     * @param domain Class Name exception created in
     * @param number Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     * @param cause Throwable that was the cause of this exception.
     */
    public OpenCDXUnimplemented(String domain, int number, String message, Throwable cause) {
        super(HttpStatus.NOT_IMPLEMENTED, Code.UNIMPLEMENTED, domain, number, message, cause);
    }

    /**
     * Constructor for Exception.
     * @param domain Class Name exception created in
     * @param number Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     * @param cause Throwable that was the cause of this exception.
     * @param enableSuppression Boolean to indicate allow suppression.
     * @param writableStackTrace Boolean to indicate writeable stack trace.
     */
    public OpenCDXUnimplemented(
            String domain,
            int number,
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(
                HttpStatus.NOT_IMPLEMENTED,
                Code.UNIMPLEMENTED,
                domain,
                number,
                message,
                cause,
                enableSuppression,
                writableStackTrace);
    }
}
