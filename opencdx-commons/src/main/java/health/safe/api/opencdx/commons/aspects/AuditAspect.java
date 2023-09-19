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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import health.safe.api.opencdx.commons.annotations.OpenCDXAuditUser;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@EnableAspectJAutoProxy
@Component
@SuppressWarnings("java:S1874")
public class AuditAspect {
    private static final ConcurrentMap<Long, RequestActorAttributes> userInfo = new ConcurrentHashMap<>();
    private final ExpressionParser parser;

    @Autowired
    ObjectMapper objectMapper;

    AuditAspect() {
        this.parser = new SpelExpressionParser();
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @Around(value = "@annotation(health.safe.api.opencdx.commons.annotations.OpenCDXAuditUser)")
    public void auditUser(ProceedingJoinPoint proceedingJoinPoint, OpenCDXAuditUser openCDXAuditUser) throws Throwable {

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Map<String, Object> parameterMap = this.createParameterMap(signature.getParameterNames(), proceedingJoinPoint.getArgs());

        String actor = getValueFromParameter(openCDXAuditUser.actor().toString(),parameterMap).toString();
        String patient = getValueFromParameter(openCDXAuditUser.patient().toString(),parameterMap).toString();
        setCurrentThreadInfo(actor, patient);
        try {
            proceedingJoinPoint.proceed();
        } finally {
            // clear
            removeCurrentThreadInfo();
        }
    }

    /**
     * Handle looking up code based on the #
     * @param key Key to handle
     * @param parameterMap List of parameters being passed in.
     * @return Object mapping to the key
     */
    private Object getValueFromParameter(String key, Map<String,Object> parameterMap) {
        Object value = null;
        Object rootObject = parameterMap.get(key);
        if(rootObject != null) {
            if(rootObject instanceof String) {
                value =  rootObject;
            } else if(!key.contains(".")) {
                value = rootObject;
            } else {
                Expression expression = this.parser.parseExpression(key.substring(key.indexOf(".")));
                value = expression.getValue(new StandardEvaluationContext(rootObject));
            }
        }
        return value;
    }

    /**
     * Method used to create parameter map for the Audit Call
     * @param parameterNames Array of names for the parameters
     * @param values Values of the parameteres
     * @return Map containing the associate parameter to values
     * @throws JsonProcessingException Error processing data.
     */
    private Map<String,Object> createParameterMap(String[] parameterNames, Object[] values) {
        Map<String, Object> parameterMap = new HashMap<>();

        for(int i = 0; i < parameterNames.length;i++) {
            if(i < values.length) {
                parameterMap.put(parameterNames[i], values[i]);
            } else {
                parameterMap.put(parameterNames[i], null);
            }
        }

        return parameterMap;
    }



    public static RequestActorAttributes getCurrentThreadInfo() {
        log.debug("Clearing Current Thread: {}", Thread.currentThread().getName());
        return userInfo.get(Thread.currentThread().getId());
    }

    public static void setCurrentThreadInfo(String actor, String patient) {
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
