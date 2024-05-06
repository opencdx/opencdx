/*
 * Copyright 2024 Safe Health Systems, Inc.
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
package cdx.opencdx.audit.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.data.OpenCDXRecord;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.types.AuditEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * Model for AuditEvent in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@OpenCDXRecord(value="audit")
//@Document(value="audit")
public class AuditEventModel {
    @Id
    private OpenCDXIdentifier id;

    private Instant created;
    private AuditEventType eventType;
    private Actor actor;
    private DataObject dataObject;
    private String purposeOfUse;
    private AuditSource auditSource;
    private AuditEntity auditEntity;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;

    /**
     * Create AuditEventModel entity from this protobuf message
     * @param auditEvent Protobuf message to use for creation.
     */
    public AuditEventModel(AuditEvent auditEvent) {
        log.trace("Creating AuditEventModel from protobuf message");
        if (auditEvent.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    auditEvent.getCreated().getSeconds(),
                    auditEvent.getCreated().getNanos());
        }
        if (auditEvent.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    auditEvent.getModified().getSeconds(),
                    auditEvent.getModified().getNanos());
        }
        if (auditEvent.hasCreator()) {
            this.creator = new OpenCDXIdentifier(auditEvent.getCreator());
        }
        if (auditEvent.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(auditEvent.getModifier());
        }

        this.eventType = auditEvent.getEventType();
        this.actor = auditEvent.getActor();
        this.dataObject = auditEvent.getDataObject();
        this.purposeOfUse = auditEvent.getPurposeOfUse();
        this.auditSource = auditEvent.getAuditSource();
        this.auditEntity = auditEvent.getAuditEntity();
    }
}
