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
import health.safe.api.opencdx.commons.annotations.OpenCDXAuditUser;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;
import health.safe.api.opencdx.commons.exceptions.OpenCDXBadRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Spring AOP implementation for Audit Messages.
 */
@Slf4j
@Aspect
@EnableAspectJAutoProxy
public class AuditAspect {
    private static final ConcurrentMap<Long, RequestActorAttributes> userInfo = new ConcurrentHashMap<>();

    private final ExpressionParser parser;

    private static final String DOMAIN = "auditAspect";

    /**
     * Default constructor sets up the Expression Parser to be used.
     */
    public AuditAspect() {
        this.parser = new SpelExpressionParser();
    }

    /**
     * The OpenCDXAuditUser before processor
     * @param joinPoint The JoinPoint in the processing
     * @param openCDXAuditUser The annotation for retrieving values.
     */
    @Order(Ordered.LOWEST_PRECEDENCE)
    @Before(value = "@annotation(openCDXAuditUser)")
    public void auditUserBefore(JoinPoint joinPoint, OpenCDXAuditUser openCDXAuditUser) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Map<String, Object> parameterMap = this.createParameterMap(signature.getParameterNames(), joinPoint.getArgs());

        String actor = getValueFromParameter(openCDXAuditUser.actor(), parameterMap);
        String patient = getValueFromParameter(openCDXAuditUser.patient(), parameterMap);
        if (actor == null || patient == null) {
            throw new OpenCDXBadRequest(DOMAIN, 2, "Failed to load Actor/Patient.");
        }
        AuditAspect.setCurrentThreadInfo(actor, patient);
    }

    /**
     * The OpenCDXAuditUser after processor
     * @param joinPoint The JoinPoint in the processing
     * @param openCDXAuditUser The annotation for retrieving values.
     */
    @Order(Ordered.LOWEST_PRECEDENCE)
    @After(value = "@annotation(openCDXAuditUser)")
    public void auditUserAfter(JoinPoint joinPoint, OpenCDXAuditUser openCDXAuditUser) {
        AuditAspect.removeCurrentThreadInfo();
    }

    /**
     * Handle looking up code based on the #
     * @param key Key to handle
     * @param parameterMap List of parameters being passed in.
     * @return Object mapping to the key
     */
    protected String getValueFromParameter(String key, Map<String, Object> parameterMap) {
        String value = null;

        String search = key;
        if (key.contains(".")) {
            search = key.substring(0, key.indexOf('.'));
        }

        Object rootObject = parameterMap.get(search);
        if (rootObject != null) {
            if (rootObject instanceof String s) {
                value = s;
            } else {
                try {
                    Expression expression = this.parser.parseExpression(key.substring(key.indexOf('.') + 1));
                    Object obj = expression.getValue(new StandardEvaluationContext(rootObject));
                    if (obj != null) {
                        value = obj.toString();
                    }
                } catch (SpelEvaluationException e) {
                    throw new OpenCDXBadRequest(DOMAIN, 3, "Failed to resolve parameter.", e);
                }
            }
        }
        return value;
    }

    /**
     * Method used to create parameter map for the Audit Call
     * @param parameterNames Array of names for the parameters
     * @param values Values of the parameteres
     * @return Map containing the associate parameter to values
     */
    protected Map<String, Object> createParameterMap(String[] parameterNames, Object[] values) {
        Map<String, Object> parameterMap = new HashMap<>();

        for (int i = 0; i < parameterNames.length; i++) {
            parameterMap.put(parameterNames[i], values[i]);
        }
        return parameterMap;
    }

    /**
     * Static Method to getting the current thread information stored for audit.
     * @return RequestActorAttributes with the actor and patient informaiton.
     * @exception OpenCDXBadRequest Thrown when not audit information for thread is found.
     */
    public static RequestActorAttributes getCurrentThreadInfo() throws OpenCDXBadRequest {
        log.debug("Clearing Current Thread: {}", Thread.currentThread().getName());
        RequestActorAttributes attributes = userInfo.get(Thread.currentThread().threadId());

        if (attributes == null) {
            throw new OpenCDXBadRequest(DOMAIN, 1, "Failed to load Current Thread Information.");
        }
        return attributes;
    }

    /**
     * Sets the current thread information for audit.
     * @param actor String containing the actor id
     * @param patient String containing the patient id
     */
    public static void setCurrentThreadInfo(String actor, String patient) {
        log.debug(
                "Thread: {} being set with actor {}, patient {}",
                Thread.currentThread().getName(),
                actor,
                patient);
        userInfo.put(Thread.currentThread().threadId(), new RequestActorAttributes(actor, patient));
    }

    /**
     * Clears the current thread information.
     */
    public static void removeCurrentThreadInfo() {
        userInfo.remove(Thread.currentThread().threadId());
    }
}
