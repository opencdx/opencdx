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
package proto;

import cdx.opencdx.grpc.organization.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class IamOrganizationTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createOrganizationRequest() throws JsonProcessingException {
        log.info(
                "createOrganizationRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(CreateOrganizationRequest.newBuilder()
                                .setOrganization(this.getOrganization())
                                .build()));
    }

    @Test
    void updateOrganizationRequest() throws JsonProcessingException {
        log.info(
                "UpdateOrganizationRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(UpdateOrganizationRequest.newBuilder()
                                .setOrganization(this.getOrganization())
                                .build()));
    }

    @Test
    void getOrganizationDetailsByIdRequest() throws JsonProcessingException {
        log.info(
                "GetOrganizationDetailsByIdRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(GetOrganizationDetailsByIdRequest.newBuilder()
                                .setOrganizationId(ObjectId.get().toHexString())
                                .build()));
    }

    @Test
    void createWorkspaceRequest() throws JsonProcessingException {
        log.info(
                "CreateWorkspaceRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(CreateWorkspaceRequest.newBuilder()
                                .setWorkspace(this.getWorkSpace())
                                .build()));
    }

    @Test
    void updateWorkspaceRequest() throws JsonProcessingException {
        log.info(
                "UpdateWorkspaceRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(UpdateWorkspaceRequest.newBuilder()
                                .setWorkspace(this.getWorkSpace())
                                .build()));
    }

    @Test
    void getWorkspaceDetailsByIdRequest() throws JsonProcessingException {
        log.info(
                "GetWorkspaceDetailsByIdRequest: \n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(GetWorkspaceDetailsByIdRequest.newBuilder()
                                .setWorkspaceId(ObjectId.get().toHexString())
                                .build()));
    }

    Organization getOrganization() {
        return Organization.newBuilder()
                .setOrganizationId(ObjectId.get().toHexString())
                .setName("OrganizationName")
                .setDescription("OrganizationDescription")
                .setFoundingDate(Timestamp.newBuilder().setSeconds(1696435104))
                .setAddress("123 Main St., CA, 12345 USA")
                .setWebsite("http://")
                .setIndustry("Medical")
                .setRevenue(100.0)
                .setLogoUrl("http://")
                .addAllSocialMediaLinks(List.of("http://"))
                .setMissionStatement("Mission Statement")
                .addAllContacts(List.of(ContactInfo.newBuilder()
                        .setContactName("Contact Name")
                        .setEmail("test@opencdx.org")
                        .setPhone("1234567890")
                        .build()))
                .addAllWorkspaceIds(
                        List.of(ObjectId.get().toHexString(), ObjectId.get().toHexString()))
                .build();
    }

    Workspace getWorkSpace() {
        return Workspace.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setName("Workspace Name")
                .setDescription("Workspace Description")
                .setCreatedDate(Timestamp.newBuilder().setSeconds(1696435104))
                .setLocation("San Diego, California, United States")
                .setManager("Bob")
                .setCapacity(32)
                .addAllFacilities(List.of("Facility A", "Facility B"))
                .setWorkspaceType("Workspace Type")
                .addAllWorkspaceImageUrls(List.of("http://", "http://"))
                .setUsagePolicy("usage Policy")
                .setAvailabilitySchedule("Monday - Friday")
                .addAllDepartments(List.of(Department.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setName("Department Name")
                        .setDescription("Department Description")
                        .addAllEmployees(List.of(Employee.newBuilder()
                                .setEmployeeId(ObjectId.get().toHexString())
                                .setName("Employee Name")
                                .setTitle("Employee Title")
                                .setEmail("employee@deparment")
                                .setPhone("1234567890")
                                .build()))
                        .build()))
                .build();
    }
}
