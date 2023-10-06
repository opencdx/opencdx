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
package cdx.opencdx.iam.controller;

import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import cdx.opencdx.iam.service.impl.OpenCDXIAMUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.model.OpenCDXIAMUserModel;
import health.safe.api.opencdx.commons.repository.OpenCDXIAMUserRepository;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXIAMUserGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    OpenCDXIAMUserService openCDXIAMUserService;

    OpenCDXIAMUserGrpcController openCDXIAMUserGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXIAMUserRepository = Mockito.mock(OpenCDXIAMUserRepository.class);
        Mockito.when(this.openCDXIAMUserRepository.save(Mockito.any(OpenCDXIAMUserModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper, this.openCDXAuditService, this.openCDXIAMUserRepository);
        this.openCDXIAMUserGrpcController = new OpenCDXIAMUserGrpcController(this.openCDXIAMUserService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMUserRepository);
    }
}
