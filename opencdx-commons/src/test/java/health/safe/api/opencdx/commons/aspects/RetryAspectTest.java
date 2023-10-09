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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

class RetryAspectTest {
    @Test
    void testRetryOperation() {
        RetryAspectTestInstance instance = new RetryAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        RetryAspect retryAspect = new RetryAspect();
        factory.addAspect(retryAspect);
        RetryAspectTestInstance proxy = factory.getProxy();
        Assertions.assertThrows(RuntimeException.class, () -> proxy.retryTestMethod());
    }

    @Test
    void testRetryOperationForSuccess() {
        RetryAspectTestInstance instance = new RetryAspectTestInstance();
        AspectJProxyFactory factory = new AspectJProxyFactory(instance);
        RetryAspect retryAspect = new RetryAspect();
        factory.addAspect(retryAspect);
        RetryAspectTestInstance proxy = factory.getProxy();
        Assertions.assertDoesNotThrow(() -> proxy.retryTestMethodOneTry());
    }
}
