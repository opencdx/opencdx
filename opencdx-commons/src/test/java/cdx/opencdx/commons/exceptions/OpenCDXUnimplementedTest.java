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
package cdx.opencdx.commons.exceptions;

import com.google.rpc.Code;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

class OpenCDXUnimplementedTest {
    @ParameterizedTest
    @MethodSource("createExceptionsParameters")
    void createExceptions(
            String domain,
            int number,
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {

        Assertions.assertNotNull(new OpenCDXUnimplemented(domain, number, message));
        Assertions.assertNotNull(new OpenCDXUnimplemented(domain, number, message, cause));
        Assertions.assertNotNull(
                new OpenCDXUnimplemented(domain, number, message, cause, enableSuppression, writableStackTrace));
    }

    private static Stream<Arguments> createExceptionsParameters() {
        return Stream.of(
                Arguments.of("ExceptionTest", 1, "Message", new NullPointerException(), true, true),
                Arguments.of("ExceptionTest", 2, "Message", new NullPointerException(), true, true),
                Arguments.of("ExceptionTest", 3, "Message", new NullPointerException(), false, true),
                Arguments.of("ExceptionTest", 4, "Message", new NullPointerException(), false, false),
                Arguments.of("ExceptionTest", 5, "Message", new NullPointerException(), true, false),
                Arguments.of("ExceptionTest", 6, "Message", null, true, true),
                Arguments.of("ExceptionTest", 7, null, new NullPointerException(), true, true),
                Arguments.of("ExceptionTest", 8, "Message", new NullPointerException(), true, true),
                Arguments.of(null, 9, "Message", new NullPointerException(), true, true));
    }

    @Test
    void testMetaData() {
        OpenCDXException exception = new OpenCDXUnimplemented("TEST", 1, "Message");
        HashMap<String, String> metaData = new HashMap<>();
        metaData.put("Test", "1");

        exception.setMetaData(metaData);
        Assertions.assertEquals(metaData, exception.getMetaData());
    }

    @Test
    void testStatus() {
        OpenCDXException exception = new OpenCDXUnimplemented("TEST", 1, "Message");
        Assertions.assertNotNull(exception.getGrpcStatus(null));
    }

    @Test
    void testStatusMetaData() {
        OpenCDXException exception = new OpenCDXUnimplemented("TEST", 1, "Message");
        HashMap<String, String> metaData = new HashMap<>();
        metaData.put("Test", "1");

        exception.setMetaData(metaData);
        Assertions.assertNotNull(exception.getGrpcStatus(null));
    }

    @Test
    void testStatusNullMessage() {
        OpenCDXException exception = new OpenCDXUnimplemented("TEST", 1, null);
        Assertions.assertNotNull(exception.getGrpcStatus(null));
    }

    @Test
    void testStatusCause() {
        OpenCDXException exception = new OpenCDXUnimplemented("TEST", 1, "Message", new NullPointerException());
        Assertions.assertNotNull(exception.getGrpcStatus(null));
    }

    @Test
    void testStatusCauseMessage() {
        OpenCDXException exception =
                new OpenCDXUnimplemented("TEST", 1, "Message", new NullPointerException("Cause Message"));
        Assertions.assertNotNull(exception.getGrpcStatus(null));
    }

    @Test
    void testToString() {
        OpenCDXException exception =
                new OpenCDXUnimplemented("TEST", 1, "Message", new NullPointerException("Cause Message"));

        Assertions.assertNotNull(exception.toString());
    }

    @Test
    void testHttpStatus() {
        OpenCDXException exception =
                new OpenCDXUnimplemented("TEST", 1, "Message", new NullPointerException("Cause Message"));
        Assertions.assertEquals(HttpStatus.NOT_IMPLEMENTED, exception.getStatus());
    }

    @Test
    void testGetCode() {
        OpenCDXException exception =
                new OpenCDXUnimplemented("TEST", 1, "Message", new NullPointerException("Cause Message"));
        Assertions.assertEquals(Code.UNIMPLEMENTED, exception.getCode());
    }

    @Test
    void testGetRestStatus() {
        WebRequest request = Mockito.mock(WebRequest.class);
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("Request", new String[] {"test", "bob"});
        Mockito.when(request.getParameterMap()).thenReturn(parameterMap);
        Mockito.when(request.getDescription(false)).thenReturn("/api/v1/test");

        OpenCDXException exception =
                new OpenCDXUnimplemented("TEST", 1, "Message", new NullPointerException("Cause Message"));

        Assertions.assertNotNull(exception.getRestStatus(request));
    }

    @Test
    void testGetRestStatusNoMessage() {
        WebRequest request = Mockito.mock(WebRequest.class);
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("Request", new String[] {"test", "bob"});
        Mockito.when(request.getParameterMap()).thenReturn(parameterMap);
        Mockito.when(request.getDescription(false)).thenReturn("/api/v1/test");

        OpenCDXException exception = new OpenCDXUnimplemented("TEST", 1, "Message", new NullPointerException());

        Assertions.assertNotNull(exception.getRestStatus(request));
    }
}
