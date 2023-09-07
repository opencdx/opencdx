package health.safe.api.opencdx.commons.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusMessageTest {

    @Test
    void statusMessageTest() {
        Assertions.assertNotNull( new StatusMessage());
    }
}