package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXAuditServiceImplTest {

    OpenCDXAuditService openCDXAuditService;


    @BeforeEach
    void setUp() {
        openCDXAuditService = new OpenCDXAuditServiceImpl(new NoOpOpenCDXMessageServiceImpl(),"test");
    }

    @Test
    void userLogout() {
        Assertions.assertDoesNotThrow(() -> openCDXAuditService.userLogout("test", AgentType.AGENT_TYPE_HUMAN_USER,"Logout"));
    }

    @Test
    void userAccessChange() {
        Assertions.assertDoesNotThrow(() -> openCDXAuditService.userAccessChange("test", AgentType.AGENT_TYPE_HUMAN_USER,"Access Change","test"));
    }

    @Test
    void phiUpdated() {
        Assertions.assertDoesNotThrow(() -> openCDXAuditService.phiUpdated("test", AgentType.AGENT_TYPE_HUMAN_USER,"PHI Updated", SensitivityLevel.SENSITIVITY_LEVEL_HIGH,"test","resource","jsonRecord"));
    }

    @Test
    void phiDeleted() {
        Assertions.assertDoesNotThrow(() -> openCDXAuditService.phiDeleted("test", AgentType.AGENT_TYPE_HUMAN_USER,"PHI Deleted", SensitivityLevel.SENSITIVITY_LEVEL_HIGH,"test","resource","jsonRecord"));
    }
}