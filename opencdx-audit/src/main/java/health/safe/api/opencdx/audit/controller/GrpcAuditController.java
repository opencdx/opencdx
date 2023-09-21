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
package health.safe.api.opencdx.audit.controller;

import cdx.open_audit.v2alpha.AuditEvent;
import cdx.open_audit.v2alpha.AuditServiceGrpc;
import cdx.open_audit.v2alpha.AuditStatus;
import health.safe.api.opencdx.audit.handlers.OpenCDXAuditMessageHandler;
import io.grpc.stub.StreamObserver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * gRPC Controller for Audit Service
 */
@Slf4j
@GRpcService
@Observed(name = "opencdx-audit")
public class GrpcAuditController extends AuditServiceGrpc.AuditServiceImplBase {

    private final OpenCDXAuditMessageHandler openCDXAuditMessageHandler;

    /**
     * Constructor to handle processing by using the OpenCDXAuditMessageHandler.
     * @param openCDXAuditMessageHandler Handler for processing AuditEvents
     */
    public GrpcAuditController(OpenCDXAuditMessageHandler openCDXAuditMessageHandler) {
        this.openCDXAuditMessageHandler = openCDXAuditMessageHandler;
    }

    /**
     * event gRPC Service call
     * @param request Request the process
     * @param responseObserver Observer to process the response
     */
    @Override
    public void event(AuditEvent request, StreamObserver<AuditStatus> responseObserver) {

        this.openCDXAuditMessageHandler.processAuditEvent(request);

        responseObserver.onNext(AuditStatus.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }
}
