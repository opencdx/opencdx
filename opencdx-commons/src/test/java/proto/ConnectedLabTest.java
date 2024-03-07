package proto;

import cdx.opencdx.grpc.connected.BasicInfo;
import cdx.opencdx.grpc.lab.connected.LabFindings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
public class ConnectedLabTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testLabFindings() throws JsonProcessingException {
        LabFindings labFindings = LabFindings.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setWorkspaceId(ObjectId.get().toHexString())
                        .setPatientId(ObjectId.get().toHexString())
                        .setOrganizationId(ObjectId.get().toHexString())
                        .setType("lab")
                        .setVendorLabTestId(UUID.randomUUID().toString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setHealthServiceId(UUID.randomUUID().toString())
                        .setSource("lab")
                        .build())
                .build();
        log.info(
                "LabFindings: {}",
                this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(labFindings));
    }
}
