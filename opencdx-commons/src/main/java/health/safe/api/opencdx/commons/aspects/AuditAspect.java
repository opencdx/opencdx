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

import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@EnableAspectJAutoProxy
@Component
public class AuditAspect {

    private final ObjectMapper objectMapper;
    private final ExpressionParser parser;

    private static final ConcurrentMap<Long, RequestActorAttributes> userInfo = new ConcurrentHashMap<>();

    public AuditAspect() {

        this.objectMapper = new ObjectMapper();
        this.parser = new SpelExpressionParser();
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @Around(value = "@annotation(health.safe.api.opencdx.commons.annotations.OpenCDXAuditUser)")
    public void auditUser(ProceedingJoinPoint proceedingJoinPoint) {}

    public static RequestActorAttributes getCurrentThreadInfo() {
        log.debug("Clearing Current Thread: {}", Thread.currentThread().getName());
        return userInfo.get(Thread.currentThread().getId());
    }

    public static void setCurrentThreadInfo(UUID actor, UUID patient) {
        log.debug(
                "Thread: {} being set with actor {}, patient {}",
                Thread.currentThread().getName(),
                actor.toString(),
                patient.toString());
        userInfo.put(Thread.currentThread().getId(), new RequestActorAttributes(actor, patient));
    }

    public static void removeCurrentThreadInfo() {
        userInfo.remove(Thread.currentThread().getId());
    }
}
