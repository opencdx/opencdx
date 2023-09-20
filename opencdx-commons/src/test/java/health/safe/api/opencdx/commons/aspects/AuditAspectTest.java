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

import health.safe.api.opencdx.commons.dto.RequestActorAttributes;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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

    @Test
    void testAuditAspectBeforeAfter() {

        String[] parameterNames = {"actor", "patient"};
        Object[] values = {"the-actor", "the-patient"};
        AuditAspectServiceTest testService = new AuditAspectServiceTest("actor", "patient");
        Mockito.when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        Mockito.when(methodSignature.getParameterNames()).thenReturn(parameterNames);
        Mockito.when(proceedingJoinPoint.getArgs()).thenReturn(values);
        auditAspect.auditUserBefore(proceedingJoinPoint, testService);
        RequestActorAttributes attr = AuditAspect.getCurrentThreadInfo();
        Assertions.assertNotNull(attr);
        Assertions.assertNotEquals("this-actor", attr.getActor());
        auditAspect.auditUserAfter(proceedingJoinPoint, testService);
        attr = AuditAspect.getCurrentThreadInfo();
        Assertions.assertNull(attr);
    }

    @Test
    void testCreateParameterMap() {
        String[] parameterNames = {"actor", "patient"};
        Object[] values = {"the-actor", "the-patient"};
        Map<String, Object> parameterMap = auditAspect.createParameterMap(parameterNames, values);
        Assertions.assertNotNull(parameterMap);
        Object values1[] = {"the-actor"};
        parameterMap = auditAspect.createParameterMap(parameterNames, values1);
        Assertions.assertNotNull(parameterMap);
    }

    @Test
    void testGetValueFromParameter() {
        String[] parameterNames = {"actor", "patient"};
        Object[] values = {"the-actor", "the-patient"};
        Map<String,Object> parameterMap = auditAspect.createParameterMap(parameterNames, values);
        Object value = auditAspect.getValueFromParameter("actor", parameterMap);
        Assertions.assertNotNull(value);
        value =  auditAspect.getValueFromParameter("fred", parameterMap);
        Assertions.assertNull(value);
        value = parameterMap;
        parameterMap.put("actor", value);
        value =  auditAspect.getValueFromParameter("actor", parameterMap);
        Object value1 =  new RequestActorAttributes("fred", "test");
        parameterMap.put("test.bytes.length", value1);
        parameterMap.put(".actor", value1);
        value =  auditAspect.getValueFromParameter("test.bytes.length", parameterMap);
    }



    @Test
    void testThreadInfo() {
        AuditAspect.setCurrentThreadInfo("actor", "patient");
        RequestActorAttributes attr = AuditAspect.getCurrentThreadInfo();
        Assertions.assertNotNull(attr);
        Assertions.assertEquals("actor", attr.getActor());
        Assertions.assertEquals("patient", attr.getPatient());
        AuditAspect.removeCurrentThreadInfo();
        attr = AuditAspect.getCurrentThreadInfo();
        Assertions.assertNull(attr);
    }
}
