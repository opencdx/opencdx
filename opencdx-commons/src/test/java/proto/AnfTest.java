package proto;

import cdx.opencdx.grpc.anf.AnfStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class AnfTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testAnfStatement() throws JsonProcessingException {
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .setId(AnfStatement.Identifier.newBuilder().setId(ObjectId.get().toHexString()).build())
                .setTime(AnfStatement.Measure.newBuilder().setUpperBound(100.0F).setLowerBound(0.0F).setIncludeLowerBound(true).setIncludeUpperBound(true).setResolution(1.0F).setSemantic(AnfStatement.LogicalExpression.newBuilder().setExpression("expression").build()).build())
                .setSubjectOfRecord(AnfStatement.Participant.newBuilder().setId(ObjectId.get().toHexString()).build())
                .addAllAuthor(List.of(AnfStatement.Practitioner.newBuilder().setPractitioner("practitioner").setCode(AnfStatement.LogicalExpression.newBuilder().setExpression("expression").build()).setId(ObjectId.get().toHexString()).build()))
                .setSubjectOfInformation(AnfStatement.LogicalExpression.newBuilder().setExpression("expression").build())
                .addAllAssociatedStatement(List.of(AnfStatement.AssociatedStatement.newBuilder().setDescription("Associated description").build()))
                .setTopic(AnfStatement.LogicalExpression.newBuilder().setExpression("Topic").build())
                .setType(AnfStatement.LogicalExpression.newBuilder().setExpression("Type").build())
                .setNarrativeCircumstance(AnfStatement.NarrativeCircumstance.newBuilder().setText("Narrative Circumstance").build())
                .build();

        log.info("ANFStatement: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement));
    }
}
