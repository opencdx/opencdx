package health.safe.api.opencdx.tinkar.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class RestSearchControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    void search() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/search")
                        .content("{\"name\": \"jeff\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("query","chronic disease of respiratory")
                        .param("maxResults","10")
                )
                .andExpect(status().is(500))
                .andReturn();
    }

    @Test
    void getEntity() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/search/nid")
                        .content("{\"name\": \"jeff\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("nid","1")
                )
                .andExpect(status().is(500))
                .andReturn();
    }
}