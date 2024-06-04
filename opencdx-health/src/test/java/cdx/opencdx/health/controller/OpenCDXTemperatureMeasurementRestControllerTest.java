package cdx.opencdx.health.controller;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.data.TemperatureMeasurement;
import cdx.opencdx.grpc.service.health.CreateTemperatureMeasurementRequest;
import cdx.opencdx.grpc.service.health.ListTemperatureMeasurementsRequest;
import cdx.opencdx.grpc.service.health.UpdateTemperatureMeasurementRequest;
import cdx.opencdx.health.model.OpenCDXTemperatureMeasurementModel;
import cdx.opencdx.health.repository.OpenCDXTemperatureMeasurementRepository;
import cdx.opencdx.health.service.OpenCDXTemperatureMeasurementService;
import cdx.opencdx.health.service.impl.OpenCDXTemperatureMeasurementServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXTemperatureMeasurementRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @MockBean
    OpenCDXTemperatureMeasurementRepository openCDXTemperatureMeasurementRepository;

    OpenCDXTemperatureMeasurementService temperatureMeasurementService;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    @MockBean
    Connection connection;

    OpenCDXTemperatureMeasurementRestController openCDXTemperatureMeasurementRestController;

    @BeforeEach
    public void setup() {

        Mockito.when(this.openCDXTemperatureMeasurementRepository.save(Mockito.any(OpenCDXTemperatureMeasurementModel.class)))
                .thenAnswer(new Answer<OpenCDXTemperatureMeasurementModel>() {
                    @Override
                    public OpenCDXTemperatureMeasurementModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXTemperatureMeasurementModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                            argument.setPatientId(OpenCDXIdentifier.get());
                            argument.setNationalHealthId(OpenCDXIdentifier.get().toHexString());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXTemperatureMeasurementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXTemperatureMeasurementModel>>() {
                    @Override
                    public Optional<OpenCDXTemperatureMeasurementModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXTemperatureMeasurementModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .nationalHealthId(argument.toHexString())
                                .build());
                    }
                });
        Mockito.when(this.openCDXTemperatureMeasurementRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXTemperatureMeasurementModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        this.temperatureMeasurementService = new OpenCDXTemperatureMeasurementServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXTemperatureMeasurementRepository);
        this.openCDXTemperatureMeasurementRestController =
                new OpenCDXTemperatureMeasurementRestController(temperatureMeasurementService);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
        Mockito.reset(this.openCDXTemperatureMeasurementRepository);
    }

    @Test
    void getTemperatureMeasurementRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/vitals/temperature/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void createTemperatureMeasurementRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/vitals/temperature")
                        .content(this.objectMapper.writeValueAsString(CreateTemperatureMeasurementRequest.newBuilder()
                                .setTemperatureMeasurement(TemperatureMeasurement.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void updateTemperatureMeasurementRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/vitals/temperature")
                        .content(this.objectMapper.writeValueAsString(UpdateTemperatureMeasurementRequest.newBuilder()
                                .setTemperatureMeasurement(TemperatureMeasurement.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void deleteTemperatureMeasurement() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/vitals/temperature/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void listTemperatureMeasurementRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/vitals/temperature/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ListTemperatureMeasurementsRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }
}

