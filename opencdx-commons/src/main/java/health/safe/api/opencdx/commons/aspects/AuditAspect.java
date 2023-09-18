package health.safe.api.opencdx.commons.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Aspect
@EnableAspectJAutoProxy
@Component
public class AuditAspect {

    private final ObjectMapper objectMapper;
    private final ExpressionParser parser;

    private static final ConcurrentMap<Long, RequestActorAttributes> userInfo =
            new ConcurrentHashMap<>();

    public AuditAspect() {
        this.objectMapper = new ObjectMapper();
        this.parser = new SpelExpressionParser();
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    //@Around(value = "annotation()")
    public void auditUser(Joinpoint joinPoint) {
    }

    public static RequestActorAttributes getCurrentThreadInfo() {
        log.debug("Clearing Current Thread: {}", Thread.currentThread().getName());
        return userInfo.get(Thread.currentThread().getId());
    }

    public static void setCurrentThreadInfo(UUID actor, UUID patient) {
        log.debug("Thread: {} being set with actor {}, patient {}",
                Thread.currentThread().getName(), actor.toString(), patient.toString());
        userInfo.put(Thread.currentThread().getId(), new RequestActorAttributes(actor, patient));
    }

    public static void removeCurrentThreadInfo() {
        userInfo.remove(Thread.currentThread().getId());
    }
}
