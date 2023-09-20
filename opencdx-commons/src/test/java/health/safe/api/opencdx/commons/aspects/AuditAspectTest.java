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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
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
            AuditAspectTestInstance.class
        })
@ExtendWith(SpringExtension.class)
class AuditAspectTest {
    @Test
    void testOpenCDXAuditUser() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect();
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
        AuditAspect auditAspect = new AuditAspect();
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
        AuditAspect auditAspect = new AuditAspect();
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertThrows(
                OpenCDXBadRequest.class, () -> proxy.testAnnotationFail(new RequestActorAttributes("Bob", "Jim")));
    }

    @Test
    void testOpenCDXAuditUserFail2() {
        AuditAspectTestInstance instance = new AuditAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        AuditAspect auditAspect = new AuditAspect();
        factory.addAspect(auditAspect);
        AuditAspectTestInstance proxy = factory.getProxy();
        Assertions.assertThrows(
                OpenCDXBadRequest.class, () -> proxy.testAnnotationFail2(new RequestActorAttributes("Bob", "Jim")));
    }
}
