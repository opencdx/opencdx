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

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Spring AOP implementation for Retry Messages.
 */
@Slf4j
@Aspect
@EnableAspectJAutoProxy
public class RetryAspect {

    /**
     * Default constructor Retry Aspect.
     */
    public RetryAspect() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * The RetryAspect for operation.
     * @param joinPoint The ProceedingJoinPoint in the processing.
     * @return Object to process the request further.
     * @throws Throwable thrown by ProceedingJoinPoint proceed method.
     */
    @Order(Ordered.LOWEST_PRECEDENCE)
    @Around(value = "@annotation(health.safe.api.opencdx.commons.annotations.RetryAnnotation)")
    public Object retryOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Processing RetryAnnotation Annotation");
        int numAttempts = 0;
        RuntimeException exception;
        do {
            try {
                log.info("Retrying as number of attempts remain less than 5 , attempt number {}", numAttempts);
                return joinPoint.proceed();
            } catch (RuntimeException e) {
                numAttempts++;
                exception = e;
            }
        } while (numAttempts < 5);
        throw exception;
    }
}
