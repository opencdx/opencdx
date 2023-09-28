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

import health.safe.api.opencdx.commons.annotations.OpenCDXAuditAnnotation;
import health.safe.api.opencdx.commons.annotations.OpenCDXAuditUser;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;
import health.safe.api.opencdx.commons.exceptions.OpenCDXBadRequest;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
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
    public static final String EMPTY_VALUE_FOR_ONE_OF_ACTOR_PURPOSE = "Empty value for one of actor/purpose";
    public static final String EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE =
            "Empty value for one of actor/patient/data/purpose/resource";

    private final ExpressionParser parser;

    private static final String DOMAIN = "auditAspect";

    private final OpenCDXAuditService auditService;

    /**
     * Default constructor sets up the Expression Parser to be used.
     * @param auditService
     */
    public AuditAspect(OpenCDXAuditService auditService) {
        this.auditService = auditService;
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
        log.info("Processing OpenCDXAuditUser Annotation");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Map<String, Object> parameterMap = this.createParameterMap(signature.getParameterNames(), joinPoint.getArgs());

        String actor = getValueFromParameter(openCDXAuditUser.actor(), parameterMap);
        String patient = getValueFromParameter(openCDXAuditUser.patient(), parameterMap);
        if (actor == null || patient == null) {
            throw new OpenCDXBadRequest(DOMAIN, 1, "Failed to load Actor/Patient.");
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
        log.info("Completed Processing OpenCDXAuditUser Annotation");
    }

    /**
     * The OpenCDXAuditAnnotation before processor
     * @param joinPoint The JoinPoint in the processing
     * @param openCDXAuditAnnotation The annotation for retrieving values.
     */
    @Order(Ordered.LOWEST_PRECEDENCE)
    @Before(value = "@annotation(openCDXAuditAnnotation)")
    public void auditAnnotationBefore(JoinPoint joinPoint, OpenCDXAuditAnnotation openCDXAuditAnnotation) {
        log.info("Processing OpenCDXAuditAnnotation Annotation");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Map<String, Object> parameterMap = this.createParameterMap(signature.getParameterNames(), joinPoint.getArgs());

        String actor = getValueFromParameter(openCDXAuditAnnotation.actor(), parameterMap);
        String patient = getValueFromParameter(openCDXAuditAnnotation.patient(), parameterMap);
        String data = getValueFromParameter(openCDXAuditAnnotation.data(), parameterMap);

        if (actor == null || patient == null || data == null) {
            throw new OpenCDXBadRequest(DOMAIN, 2, "Null value for one of actor/patient/data");
        }
        switch (openCDXAuditAnnotation.eventType()) {
            case AUDIT_EVENT_TYPE_USER_PII_ACCESSED:
                userPiiAccessed(openCDXAuditAnnotation, actor, patient, data);
                break;
            case AUDIT_EVENT_TYPE_USER_PII_UPDATED:
                userPiiUpdated(openCDXAuditAnnotation, actor, patient, data);
                break;
            case AUDIT_EVENT_TYPE_USER_PII_CREATED:
                userPiiCreated(openCDXAuditAnnotation, actor, patient, data);
                break;
            case AUDIT_EVENT_TYPE_USER_PII_DELETED:
                userPiiDeleted(openCDXAuditAnnotation, actor, patient, data);
                break;
            case AUDIT_EVENT_TYPE_USER_PHI_ACCESSED:
                userPhiAccessed(openCDXAuditAnnotation, actor, patient, data);
                break;
            case AUDIT_EVENT_TYPE_USER_PHI_UPDATED:
                userPhiUpdated(openCDXAuditAnnotation, actor, patient, data);
                break;
            case AUDIT_EVENT_TYPE_USER_PHI_CREATED:
                userPhiCreated(openCDXAuditAnnotation, actor, patient, data);
                break;
            case AUDIT_EVENT_TYPE_USER_PHI_DELETED:
                userPhiDeleted(openCDXAuditAnnotation, actor, patient, data);
                break;
            case AUDIT_EVENT_TYPE_USER_COMMUNICATION:
                userCommunication(openCDXAuditAnnotation, actor, patient, data);
                break;
            case AUDIT_EVENT_TYPE_CONFIG_CHANGE:
                configChange(openCDXAuditAnnotation, actor, data);
                break;
            case AUDIT_EVENT_TYPE_USER_LOGIN_SUCCEEDED:
                userLoginSucceeded(openCDXAuditAnnotation, actor);
                break;
            case AUDIT_EVENT_TYPE_USER_LOGIN_FAIL:
                userLoginFail(openCDXAuditAnnotation, actor);
                break;
            case AUDIT_EVENT_TYPE_USER_LOG_OUT:
                userLogout(openCDXAuditAnnotation, actor);
                break;
            case AUDIT_EVENT_TYPE_USER_ACCESS_CHANGE:
                userAccessChange(openCDXAuditAnnotation, actor, patient);
                break;
            case AUDIT_EVENT_TYPE_USER_PASSWORD_CHANGE:
                userPassworChange(openCDXAuditAnnotation, actor, patient);
                break;
            default:
                throw new OpenCDXBadRequest(DOMAIN, 18, "Event Type provided does not match");
        }
        log.info("Completed Processing OpenCDXAuditAnnotation Annotation");
    }

    private void userPassworChange(OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(patient)) {
            auditService.passwordChange(
                    actor, openCDXAuditAnnotation.agentType(), openCDXAuditAnnotation.purpose(), patient);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 17, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PURPOSE);
        }
    }

    private void userAccessChange(OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(patient)) {
            auditService.userAccessChange(
                    actor, openCDXAuditAnnotation.agentType(), openCDXAuditAnnotation.purpose(), patient);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 16, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PURPOSE);
        }
    }

    private void userLogout(OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor) {
        if (StringUtils.isNotEmpty(actor) && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())) {
            auditService.userLogout(actor, openCDXAuditAnnotation.agentType(), openCDXAuditAnnotation.purpose());
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 15, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PURPOSE);
        }
    }

    private void userLoginFail(OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor) {
        if (StringUtils.isNotEmpty(actor) && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())) {
            auditService.userLoginFailure(actor, openCDXAuditAnnotation.agentType(), openCDXAuditAnnotation.purpose());
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 14, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PURPOSE);
        }
    }

    private void userLoginSucceeded(OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor) {
        if (StringUtils.isNotEmpty(actor) && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())) {
            auditService.userLoginSucceed(actor, openCDXAuditAnnotation.agentType(), openCDXAuditAnnotation.purpose());
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 13, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PURPOSE);
        }
    }

    private void configChange(OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.config(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 12, "Empty value for one of actor/data/purpose/resource");
        }
    }

    private void userCommunication(
            OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(patient)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.communication(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    patient,
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 11, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE);
        }
    }

    private void userPhiDeleted(
            OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(patient)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.phiDeleted(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    patient,
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 10, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE);
        }
    }

    private void userPhiCreated(
            OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(patient)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.phiCreated(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    patient,
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 9, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE);
        }
    }

    private void userPhiUpdated(
            OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(patient)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.phiUpdated(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    patient,
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 8, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE);
        }
    }

    private void userPhiAccessed(
            OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(patient)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.phiAccessed(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    patient,
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 7, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE);
        }
    }

    private void userPiiDeleted(
            OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(patient)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.piiDeleted(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    patient,
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 6, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE);
        }
    }

    private void userPiiCreated(
            OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(patient)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.piiCreated(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    patient,
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 5, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE);
        }
    }

    private void userPiiUpdated(
            OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(patient)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.piiUpdated(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    patient,
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 4, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE);
        }
    }

    private void userPiiAccessed(
            OpenCDXAuditAnnotation openCDXAuditAnnotation, String actor, String patient, String data) {
        if (StringUtils.isNotEmpty(actor)
                && StringUtils.isNotEmpty(patient)
                && StringUtils.isNotEmpty(data)
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.purpose())
                && StringUtils.isNotEmpty(openCDXAuditAnnotation.resource())) {
            auditService.piiAccessed(
                    actor,
                    openCDXAuditAnnotation.agentType(),
                    openCDXAuditAnnotation.purpose(),
                    openCDXAuditAnnotation.sensitivityLevel(),
                    patient,
                    openCDXAuditAnnotation.resource(),
                    data);
        } else {
            throw new OpenCDXBadRequest(DOMAIN, 3, EMPTY_VALUE_FOR_ONE_OF_ACTOR_PATIENT_DATA_PURPOSE_RESOURCE);
        }
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
