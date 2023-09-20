package health.safe.api.opencdx.commons.aspects;

import health.safe.api.opencdx.commons.annotations.OpenCDXAuditUser;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import static org.mockito.Mockito.mock;

public class AuditAspectTest {

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
    void testAuditAspect() {




        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();

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
