package health.safe.api.opencdx.commons.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RequestActorAttributesTest {

    @Test
    void testRequestActorAttributes() {
        RequestActorAttributes attr = new RequestActorAttributes("actor", "patient");
        Assertions.assertEquals("actor", attr.getActor());
        Assertions.assertEquals("patient", attr.getPatient());
    }
}
