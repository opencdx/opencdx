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
package cdx.opencdx.client.service.impl;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.grpc.questionnaire.*;
import com.google.rpc.Code;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.micrometer.observation.annotation.Observed;
import java.io.InputStream;
import javax.net.ssl.SSLException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Implementation of the Questionnaire gRPC Client.
 */
@Slf4j
@Observed(name = "opencdx")
@Service
@ConditionalOnProperty(prefix = "opencdx.client.questionnaire", name = "enabled", havingValue = "true")
@SuppressWarnings("java:S125")
public class OpenCDXQuestionnaireClientImpl implements OpenCDXQuestionnaireClient {

    public static final String OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL = "OpenCDXQuestionnaireClientImpl";
    private final QuestionnaireServiceGrpc.QuestionnaireServiceBlockingStub questionnaireServiceBlockingStub;

    /**
     * Default Constructor used for normal operation.
     * @param server Server address for the gRPC Service.
     * @param port Server port for the gRPC Service.
     * @throws SSLException creating Client
     */
    @Generated
    public OpenCDXQuestionnaireClientImpl(
            @Value("${opencdx.client.questionnaire.server}") String server,
            @Value("${opencdx.client.questionnaire.port}") Integer port)
            throws SSLException {
        InputStream certChain = getClass().getClassLoader().getResourceAsStream("opencdx-clients.pem");
        ManagedChannel channel = NettyChannelBuilder.forAddress(server, port)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(certChain).build())
                .build();
        this.questionnaireServiceBlockingStub = QuestionnaireServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Constructor for creating the Questionnaire client implementation.
     * @param questionnaireServiceBlockingStub gRPC Blocking Stub for Protector.
     */
    public OpenCDXQuestionnaireClientImpl(
            QuestionnaireServiceGrpc.QuestionnaireServiceBlockingStub questionnaireServiceBlockingStub) {
        this.questionnaireServiceBlockingStub = questionnaireServiceBlockingStub;
    }

    /**
     * Method to gRPC Call Questionnaire Service getRuleSets() api.
     *
     * @param request                Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public RuleSetsResponse getRuleSets(ClientRulesRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getRuleSets(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    1,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service createQuestionnaire() api.
     *
     * @param request                Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Questionnaire createQuestionnaire(
            QuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createQuestionnaire(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    2,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service updateQuestionnaire() api.
     *
     * @param request                Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Questionnaire updateQuestionnaire(
            QuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateQuestionnaire(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    3,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service getSubmittedQuestionnaire() api.
     *
     * @param request                Get Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Questionnaire getSubmittedQuestionnaire(
            GetQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getSubmittedQuestionnaire(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    4,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service getSubmittedQuestionnaireList() api.
     *
     * @param request                Get Questionnaire List request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public Questionnaires getSubmittedQuestionnaireList(
            GetQuestionnaireListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getSubmittedQuestionnaireList(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    5,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service deleteSubmittedQuestionnaire() api.
     *
     * @param request                Delete Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SubmissionResponse deleteSubmittedQuestionnaire(
            DeleteQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteSubmittedQuestionnaire(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    6,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service createQuestionnaireData() api.
     *
     * @param request                Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SubmissionResponse createQuestionnaireData(
            QuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    7,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service updateQuestionnaireData() api.
     *
     * @param request                Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SubmissionResponse updateQuestionnaireData(
            QuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    8,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service getQuestionnaireData() api.
     *
     * @param request                Get Questionnaire Request Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SystemQuestionnaireData getQuestionnaireData(
            GetQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    9,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service getQuestionnaireDataList() api.
     *
     * @param request                Get Questionnaire List request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SystemQuestionnaireData getQuestionnaireDataList(
            GetQuestionnaireListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getQuestionnaireDataList(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    10,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service deleteQuestionnaireRequest() api.
     *
     * @param request                Delete Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SubmissionResponse deleteQuestionnaireData(
            DeleteQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    11,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service createClientQuestionnaireData() api.
     *
     * @param request                Client Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SubmissionResponse createClientQuestionnaireData(
            ClientQuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createClientQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    12,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service updateClientQuestionnaireData() api.
     *
     * @param request                Client Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SubmissionResponse updateClientQuestionnaireData(
            ClientQuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .updateClientQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    13,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service getClientQuestionnaireData() api.
     *
     * @param request                Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ClientQuestionnaireData getClientQuestionnaireData(
            GetQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getClientQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    14,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service getClientQuestionnaireDataList() api.
     *
     * @param request                Get Questionnaire List Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public ClientQuestionnaireData getClientQuestionnaireDataList(
            GetQuestionnaireListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getClientQuestionnaireDataList(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    15,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service deleteClientQuestionnaireData() api.
     *
     * @param request                Delete Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SubmissionResponse deleteClientQuestionnaireData(
            DeleteQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .deleteClientQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    16,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service createUserQuestionnaireData() api.
     *
     * @param request                User Questionnaire Data request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public SubmissionResponse createUserQuestionnaireData(
            UserQuestionnaireDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .createUserQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    17,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }

    /**
     * Method to gRPC Call Questionnaire Service getUserQuestionnaireData() api.
     *
     * @param request                Get Questionnaire request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    @Override
    public UserQuestionnaireData getUserQuestionnaireData(
            GetQuestionnaireRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException {
        try {
            return questionnaireServiceBlockingStub
                    .withCallCredentials(openCDXCallCredentials)
                    .getUserQuestionnaireData(request);
        } catch (StatusRuntimeException e) {
            com.google.rpc.Status status = io.grpc.protobuf.StatusProto.fromThrowable(e);
            throw new OpenCDXClientException(
                    Code.forNumber(status.getCode()),
                    OPEN_CDX_QUESTIONNAIRE_CLIENT_IMPL,
                    18,
                    status.getMessage(),
                    status.getDetailsList(),
                    e);
        }
    }
}
