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

import health.safe.api.opencdx.commons.config.CommonsConfig;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;
import health.safe.api.opencdx.commons.exceptions.OpenCDXBadRequest;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("noop")
@Import(AnnotationAwareAspectJAutoProxyCreator.class)
@SpringBootTest(
        classes = {
            CommonsConfig.class,
            AuditAspect.class,
            AnnotationAwareAspectJAutoProxyCreator.class,
            AuditAspectTestInstance.class,
            ObservationAutoConfiguration.class
        })
@ExtendWith(SpringExtension.class)
class AuditAspectTest {
    @Autowired
    OpenCDXAuditService auditService;

    @Test
    void testOpenCDXAuditUser() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        proxy.testAnnotation("Bob", "Jim");
        Assertions.assertEquals("Bob", proxy.getInfo().getActor());
        Assertions.assertEquals("Jim", proxy.getInfo().getPatient());
        Assertions.assertThrows(OpenCDXBadRequest.class, () -> AuditAspect.getCurrentThreadInfo());
    }

    @Test
    void testOpenCDXAuditUserChild() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        proxy.testAnnotationChild(new RequestActorAttributes("Bob", "Jim"));
        Assertions.assertEquals("Bob", proxy.getInfo().getActor());
        Assertions.assertEquals("Jim", proxy.getInfo().getPatient());
        Assertions.assertThrows(OpenCDXBadRequest.class, () -> AuditAspect.getCurrentThreadInfo());
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

    @Test
    void testOpenCDXAuditUserFail() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        RequestActorAttributes attributes = new RequestActorAttributes("Bob", "Jim");
        Assertions.assertThrows(OpenCDXBadRequest.class, () -> proxy.testAnnotationFail(attributes));
    }

    @Test
    void testOpenCDXAuditUserFail2() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        RequestActorAttributes attributes = new RequestActorAttributes("Bob", "Jim");
        Assertions.assertThrows(OpenCDXBadRequest.class, () -> proxy.testAnnotationFail2(attributes));
    }

    @Test
    void testOpenCDXAuditUserFail3() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        RequestActorAttributes attributes = new RequestActorAttributes("Bob", "Jim");
        Assertions.assertThrows(OpenCDXBadRequest.class, () -> proxy.testAnnotationFail3(attributes));
    }

    @Test
    void testOpenCDXAuditAnnotationEventUnspecified() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationEventUnspecified("actor", "patient", "data", "purpose", "resource"));
    }

    @Test
    void testOpenCDXAuditAnnotationLoginSucceed() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserLoginSucceed("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserLoginSucceedElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserLogOut() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserLogOut("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserLogOutElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserLoginFail() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserLoginFail("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserLoginFailElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserAccessChange() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserAccessChange("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserAccessChangeElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserPasswordChange() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserPasswordChange("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserPasswordChangeElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserPIIAccessed() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserPIIAccessed("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserPIIAccessedElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserPIIUpdated() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserPIIUpdated("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserPIIUpdatedElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserPIICreated() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserPIICreated("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserPIICreatedElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserPIIDeleted() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserPIIDeleted("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserPIIDeletedElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserPHIAccessed() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserPHIAccessed("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserPHIAccessedElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserPHIUpdated() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserPHIUpdated("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserPHIUpdatedElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserPHICreated() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserPHICreated("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserPHICreatedElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserPHIDeleted() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserPHIDeleted("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserPHIDeletedElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationUserCommunication() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationUserCommunication("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationUserCommunicationElse("actor", "patient", "data", "", ""));
    }

    @Test
    void testOpenCDXAuditAnnotationConfigChange() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect(auditService);
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(
                () -> proxy.testAuditAnnotationConfigChange("actor", "patient", "data", "purpose", "resource"));
        Assertions.assertThrows(
                OpenCDXBadRequest.class,
                () -> proxy.testAuditAnnotationConfigChangeElse("actor", "patient", "data", "", ""));
        Assertions.assertThrows(
                OpenCDXBadRequest.class, () -> proxy.testAuditAnnotationConfigChange(null, "patient", "data", "", ""));
    }
}
