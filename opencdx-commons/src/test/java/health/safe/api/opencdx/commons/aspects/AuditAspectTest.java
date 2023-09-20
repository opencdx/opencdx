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
package health.safe.api.opencdx.commons.aspects;

import static org.mockito.Mockito.mock;

import health.safe.api.opencdx.commons.dto.RequestActorAttributes;
import health.safe.api.opencdx.commons.exceptions.OpenCDXBadRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

class AuditAspectTest {

    @InjectMocks
    AuditAspect auditAspect;

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    JoinPoint joinPoint;

    @Mock
    PutMapping putMapping;

    @Mock
    PostMapping postMapping;

    @Mock
    GetMapping getMapping;

    @Mock
    DeleteMapping deleteMapping;

    @Mock
    MethodSignature methodSignature;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        AuditAspect.removeCurrentThreadInfo();
    }

    @SuppressWarnings("java:S2699")
    @Test
    void testAuditAspect() {

        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    }

    @Test
    void testThreadInfo() {
        AuditAspect.setCurrentThreadInfo("actor", "patient");
        RequestActorAttributes attr = AuditAspect.getCurrentThreadInfo();
        Assertions.assertNotNull(attr);
        Assertions.assertEquals("actor", attr.getActor());
        Assertions.assertEquals("patient", attr.getPatient());
        AuditAspect.removeCurrentThreadInfo();
        Assertions.assertThrows(OpenCDXBadRequest.class, () -> AuditAspect.getCurrentThreadInfo());
    }
}
