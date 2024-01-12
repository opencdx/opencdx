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
package cdx.opencdx.commons.service.impl;

import static org.mockito.Mockito.*;

import cdx.opencdx.commons.config.CommonsConfig;
import cdx.opencdx.commons.dto.StatusMessage;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import io.micrometer.tracing.*;
import io.micrometer.tracing.Tracer;
import io.nats.client.*;
import io.nats.client.api.StreamConfiguration;
import io.nats.client.api.StreamInfo;
import io.nats.client.impl.Headers;
import io.nats.client.impl.NatsJetStreamMetaData;
import io.nats.client.support.Status;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

class NatsOpenCDXMessageServiceImplTest {

    @Mock
    Connection connection;

    @Mock
    Dispatcher dispatcher;

    ObjectMapper objectMapper;

    CommonsConfig commonsConfig;

    @Mock
    JetStreamManagement jetStreamManagement;

    @Mock
    StreamInfo streamInfo;

    @Mock
    JetStream jetStream;

    @Mock
    JetStreamSubscription jetStreamSubscription;

    @Mock
    StreamConfiguration streamConfiguration;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    brave.Tracer tracerBrave;

    @Mock
    Tracer tracer;

    @Mock
    private brave.Tracing tracingBrave;

    @Mock
    Span currentSpan;

    ;

    @Mock
    brave.Span spanBrave;

    @Mock
    Connection natsConnection;
    // private Tracer tracer1;

    @BeforeEach
    void setUp() throws IOException, JetStreamApiException {
        this.openCDXCurrentUser = mock(OpenCDXCurrentUser.class);
        this.connection = mock(Connection.class);
        this.dispatcher = mock(Dispatcher.class);
        this.jetStreamManagement = mock(JetStreamManagement.class);
        this.streamInfo = mock(StreamInfo.class);
        this.jetStream = mock(JetStream.class);
        this.jetStreamSubscription = mock(JetStreamSubscription.class);
        this.streamConfiguration = mock(StreamConfiguration.class);
        this.tracingBrave = mock(brave.Tracing.class);
        this.tracer = mock(Tracer.class);
        this.tracerBrave = mock(brave.Tracer.class);
        this.currentSpan = mock(Span.class);
        this.spanBrave = mock(brave.Span.class);
        this.objectMapper = new ObjectMapper();
        this.natsConnection = mock(Connection.class);
        this.objectMapper.registerModule(new ProtobufModule());
        this.objectMapper.registerModule(new JavaTimeModule());
        when(this.connection.createDispatcher()).thenReturn(this.dispatcher);
        when(this.connection.jetStreamManagement()).thenReturn(this.jetStreamManagement);
        when(this.jetStreamManagement.addStream(Mockito.any())).thenReturn(this.streamInfo);
        when(this.connection.jetStream()).thenReturn(this.jetStream);
        when(this.jetStream.subscribe(
                        Mockito.any(String.class),
                        Mockito.any(String.class),
                        Mockito.any(Dispatcher.class),
                        Mockito.any(MessageHandler.class),
                        Mockito.eq(true),
                        Mockito.any(PushSubscribeOptions.class)))
                .thenReturn(this.jetStreamSubscription);
        when(this.streamInfo.getConfiguration()).thenReturn(this.streamConfiguration);
        when(this.streamConfiguration.getName()).thenReturn("Test");
        when(this.tracer.nextSpan()).thenReturn(Tracer.NOOP.nextSpan());
        when(this.tracer.traceContextBuilder()).thenReturn(Tracer.NOOP.traceContextBuilder());
        when(this.tracer.spanBuilder()).thenReturn(Tracer.NOOP.spanBuilder());
        this.commonsConfig = new CommonsConfig();
    }

    @AfterEach
    void tearDown() {}

    @Test
    void NatsMessageHandler() throws InterruptedException {
        OpenCDXMessageHandler handler = new OpenCDXMessageHandler() {
            @Override
            public void receivedMessage(byte[] message) {
                Assertions.assertEquals("Test", new String(message));
            }
        };

        NatsOpenCDXMessageServiceImpl.NatsMessageHandler natsMessageHandler =
                new NatsOpenCDXMessageServiceImpl.NatsMessageHandler(handler, openCDXCurrentUser, objectMapper, tracer);

        Message msg = this.getMessage();
        Assertions.assertThrows(OpenCDXInternal.class, () -> natsMessageHandler.onMessage(msg));
    }

    @Test
    void subscribe() {
        OpenCDXMessageService service = this.commonsConfig.natsOpenCDXMessageService(
                this.connection, this.objectMapper, openCDXCurrentUser, "test", tracer);

        OpenCDXMessageHandler handler = new OpenCDXMessageHandler() {
            @Override
            public void receivedMessage(byte[] message) {
                Assertions.assertEquals("Test", new String(message));
            }
        };

        final NatsOpenCDXMessageServiceImpl.NatsMessageHandler[] natsHandler =
                new NatsOpenCDXMessageServiceImpl.NatsMessageHandler[1];

        Assertions.assertDoesNotThrow(() -> service.subscribe("Test-Message", handler));
    }

    @Test
    void unsubscribe_fail() throws JetStreamApiException, IOException {

        when(this.jetStream.subscribe(
                        Mockito.any(String.class),
                        Mockito.any(String.class),
                        Mockito.any(Dispatcher.class),
                        Mockito.any(MessageHandler.class),
                        Mockito.any(Boolean.class),
                        Mockito.any(PushSubscribeOptions.class)))
                .thenReturn(null);

        OpenCDXMessageService service = this.commonsConfig.natsOpenCDXMessageService(
                this.connection, this.objectMapper, openCDXCurrentUser, "test", tracer);

        OpenCDXMessageHandler handler = new OpenCDXMessageHandler() {
            @Override
            public void receivedMessage(byte[] message) {
                Assertions.assertEquals("Test", new String(message));
            }
        };

        final NatsOpenCDXMessageServiceImpl.NatsMessageHandler[] natsHandler =
                new NatsOpenCDXMessageServiceImpl.NatsMessageHandler[1];

        Assertions.assertDoesNotThrow(() -> service.subscribe("Test-Message", handler));
        Assertions.assertDoesNotThrow(() -> service.unSubscribe("Test-Message"));
    }

    private Message getMessage() {
        return new Message() {
            @Override
            public String getSubject() {
                return null;
            }

            @Override
            public String getReplyTo() {
                return null;
            }

            @Override
            public boolean hasHeaders() {
                return false;
            }

            @Override
            public Headers getHeaders() {
                return null;
            }

            @Override
            public boolean isStatusMessage() {
                return false;
            }

            @Override
            public Status getStatus() {
                return null;
            }

            @Override
            public byte[] getData() {
                return "Test".getBytes();
            }

            @Override
            public boolean isUtf8mode() {
                return false;
            }

            @Override
            public Subscription getSubscription() {
                return null;
            }

            @Override
            public String getSID() {
                return null;
            }

            @Override
            public Connection getConnection() {
                return null;
            }

            @Override
            public NatsJetStreamMetaData metaData() {
                return null;
            }

            @Override
            public void ack() {}

            @Override
            public void ackSync(Duration timeout) throws TimeoutException, InterruptedException {}

            @Override
            public void nak() {}

            @Override
            public void term() {}

            @Override
            public void inProgress() {}

            @Override
            public boolean isJetStream() {
                return false;
            }
        };
    }

    @ParameterizedTest
    @MethodSource("createUnSubscribeParameters")
    void unSubscribe(boolean returnValue, String message) {
        NatsOpenCDXMessageServiceImpl service = new NatsOpenCDXMessageServiceImpl(
                this.connection, this.objectMapper, "test", openCDXCurrentUser, tracer);

        OpenCDXMessageHandler handler = new OpenCDXMessageHandler() {
            @Override
            public void receivedMessage(byte[] message) {
                Assertions.assertEquals("Test", new String(message));
            }
        };

        service.subscribe("TEST-MESSAGE", handler);

        when(this.jetStreamSubscription.isActive()).thenReturn(returnValue);
        Assertions.assertDoesNotThrow(() -> {
            service.unSubscribe(message);
        });
    }

    private static Stream<Arguments> createUnSubscribeParameters() {
        return Stream.of(
                Arguments.of(true, "TEST-MESSAGE-FAIL"),
                Arguments.of(false, "TEST-MESSAGE-FAIL"),
                Arguments.of(true, "TEST-MESSAGE"),
                Arguments.of(false, "TEST-MESSAGE"));
    }

    // @Test
    void send() {
        NatsOpenCDXMessageServiceImpl service = new NatsOpenCDXMessageServiceImpl(
                this.connection, this.objectMapper, "test", openCDXCurrentUser, tracer);
        ReflectionTestUtils.setField(service, "tracer", tracer);
        Span span = mock(Span.class);
        TraceContext traceContext = mock(TraceContext.class);

        when(tracer.currentSpan()).thenReturn(span);
        when(span.context()).thenReturn(traceContext);
        when(traceContext.spanId()).thenReturn("spanid");

        Assertions.assertDoesNotThrow(() -> {
            service.send("TEST-MESSAGE", new StatusMessage());
        });
    }

    // @Test
    void sendException() throws JsonProcessingException {
        ObjectMapper mapper = mock(ObjectMapper.class);
        NatsOpenCDXMessageServiceImpl service =
                new NatsOpenCDXMessageServiceImpl(this.connection, mapper, "test", openCDXCurrentUser, tracer);
        ReflectionTestUtils.setField(service, "tracer", tracer);
        when(mapper.writeValueAsBytes(Mockito.any())).thenThrow(JsonProcessingException.class);

        StatusMessage message = new StatusMessage();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            service.send("TEST-MESSAGE", message);
        });
    }

    @Test
    void exception_1() throws JetStreamApiException, IOException {
        when(this.jetStreamManagement.addStream(Mockito.any())).thenReturn(null);
        Assertions.assertThrows(
                OpenCDXInternal.class,
                () -> new NatsOpenCDXMessageServiceImpl(
                        this.connection, this.objectMapper, "test", openCDXCurrentUser, tracer));
    }

    @Test
    void exception_2() throws JetStreamApiException, IOException {
        when(this.jetStreamManagement.addStream(Mockito.any())).thenThrow(JetStreamApiException.class);
        Assertions.assertThrows(
                OpenCDXInternal.class,
                () -> new NatsOpenCDXMessageServiceImpl(
                        this.connection, this.objectMapper, "test", openCDXCurrentUser, tracer));
    }

    @Test
    void exception_3() throws JetStreamApiException, IOException {
        when(this.jetStreamManagement.addStream(Mockito.any())).thenThrow(IOException.class);
        Assertions.assertThrows(
                OpenCDXInternal.class,
                () -> new NatsOpenCDXMessageServiceImpl(
                        this.connection, this.objectMapper, "test", openCDXCurrentUser, tracer));
    }

    @Test
    void exception_4() throws JetStreamApiException, IOException {

        when(this.jetStream.subscribe(
                        Mockito.any(String.class),
                        Mockito.any(Dispatcher.class),
                        Mockito.any(MessageHandler.class),
                        Mockito.any(Boolean.class),
                        Mockito.any(PushSubscribeOptions.class)))
                .thenThrow(JetStreamApiException.class);

        OpenCDXMessageService service = this.commonsConfig.natsOpenCDXMessageService(
                this.connection, this.objectMapper, openCDXCurrentUser, "test", tracer);

        OpenCDXMessageHandler handler = message -> Assertions.assertEquals("Test", new String(message));

        Assertions.assertThrows(OpenCDXInternal.class, () -> service.subscribe("Test-Message", handler));
    }

    @Test
    void exception_5() throws JetStreamApiException, IOException {

        when(this.jetStream.subscribe(
                        Mockito.any(String.class),
                        Mockito.any(Dispatcher.class),
                        Mockito.any(MessageHandler.class),
                        Mockito.any(Boolean.class),
                        Mockito.any(PushSubscribeOptions.class)))
                .thenThrow(IOException.class);

        OpenCDXMessageService service = this.commonsConfig.natsOpenCDXMessageService(
                this.connection, this.objectMapper, openCDXCurrentUser, "test", tracer);

        OpenCDXMessageHandler handler = message -> Assertions.assertEquals("Test", new String(message));

        Assertions.assertThrows(OpenCDXInternal.class, () -> service.subscribe("Test-Message", handler));
    }

    @Test
    void NatsMessageHandlerTryBlockOnMessage() {
        OpenCDXMessageHandler handler = new OpenCDXMessageHandler() {
            @Override
            public void receivedMessage(byte[] message) {
                Assertions.assertEquals("Test", new String(message));
            }
        };

        NatsOpenCDXMessageServiceImpl.NatsMessageHandler natsMessageHandler =
                new NatsOpenCDXMessageServiceImpl.NatsMessageHandler(handler, openCDXCurrentUser, objectMapper, tracer);

        Message message = mock(Message.class);
        String jsonVal = "{\"spanId\": 123, \"traceId\": 456, \"parentId\": 789, \"json\": \"Test\"}";
        when(message.getData()).thenReturn(jsonVal.getBytes());

        Assertions.assertDoesNotThrow(() -> natsMessageHandler.onMessage(message));
    }

    @Test
    void NatsMessageHandlerTryBlockOnMessageCatch() throws InterruptedException {
        OpenCDXMessageHandler handler = new OpenCDXMessageHandler() {
            @Override
            public void receivedMessage(byte[] message) {
                Assertions.assertEquals("Test", new String(message));
            }
        };

        NatsOpenCDXMessageServiceImpl.NatsMessageHandler natsMessageHandler =
                new NatsOpenCDXMessageServiceImpl.NatsMessageHandler(handler, openCDXCurrentUser, objectMapper, tracer);

        Message message = mock(Message.class);
        String jsonVal = "{\"spanId\": 123, \"traceId\": 456, \"parentId\": 789, \"json\": \"Test\"}";
        when(message.getData()).thenReturn(jsonVal.getBytes());
        when(tracerBrave.nextSpan()).thenReturn(spanBrave);
        when(tracerBrave.newChild(any())).thenReturn(spanBrave);
        when(tracerBrave.currentSpan()).thenReturn(spanBrave);
        when(tracingBrave.tracer()).thenReturn(tracerBrave);
        when(tracerBrave.withSpanInScope(any())).thenThrow(RuntimeException.class);

        Assertions.assertDoesNotThrow(() -> natsMessageHandler.onMessage(message));
        //        try (MockedStatic<Tracing> tracingMockedStatic = Mockito.mockStatic(Tracing.class)) {
        //            tracingMockedStatic.when(Tracing::currentTracer).thenReturn(tracerBrave);
        //            tracingMockedStatic.when(Tracing::current).thenReturn(tracingBrave);
        //            Assertions.assertThrows(RuntimeException.class, () -> natsMessageHandler.onMessage(message));
        //        }
    }

    @Test
    void sendTest() {
        brave.propagation.TraceContext traceContext = mock(brave.propagation.TraceContext.class);
        when(spanBrave.context()).thenReturn(traceContext);
        when(traceContext.spanId()).thenReturn(5L);
        when(traceContext.traceId()).thenReturn(7L);
        when(traceContext.parentId()).thenReturn(1L);
        when(tracerBrave.currentSpan()).thenReturn(spanBrave);
        brave.propagation.CurrentTraceContext currentTraceContext = mock(brave.propagation.CurrentTraceContext.class);
        when(currentTraceContext.get())
                .thenReturn(brave.propagation.TraceContext.newBuilder()
                        .traceId(7L)
                        .spanId(5L)
                        .build());
        when(tracerBrave.currentSpan()).thenReturn(spanBrave);
        when(tracerBrave.newChild(any())).thenReturn(spanBrave);
        when(tracingBrave.tracer()).thenReturn(tracerBrave);
        NatsOpenCDXMessageServiceImpl service = new NatsOpenCDXMessageServiceImpl(
                this.connection, this.objectMapper, "test", openCDXCurrentUser, tracer);
        Assertions.assertDoesNotThrow(() -> service.send("subject", "ObjectTest"));

        //        try (MockedStatic<Tracing> tracingMockedStatic = Mockito.mockStatic(Tracing.class)) {
        //            tracingMockedStatic.when(Tracing::currentTracer).thenReturn(tracerBrave);
        //            tracingMockedStatic.when(Tracing::current).thenReturn(tracingBrave);
        //            NatsOpenCDXMessageServiceImpl service =
        //                    new NatsOpenCDXMessageServiceImpl(this.connection, this.objectMapper, "test",
        // openCDXCurrentUserAssertions.assertThrows(RuntimeException.class, () ->
        // natsMessageHandler.onMessage(message)););
        //            Assertions.assertDoesNotThrow(() -> service.send("subject", "ObjectTest"));
        //        }
    }

    @Test
    void sendTestCatch() throws IOException {
        brave.propagation.TraceContext traceContext = mock(brave.propagation.TraceContext.class);
        when(spanBrave.context()).thenReturn(traceContext);
        when(traceContext.spanId()).thenReturn(5L);
        when(traceContext.traceId()).thenReturn(7L);
        when(traceContext.parentId()).thenReturn(1L);
        when(tracerBrave.currentSpan()).thenReturn(spanBrave);
        brave.propagation.CurrentTraceContext currentTraceContext = mock(brave.propagation.CurrentTraceContext.class);
        when(currentTraceContext.get())
                .thenReturn(brave.propagation.TraceContext.newBuilder()
                        .traceId(7L)
                        .spanId(5L)
                        .build());
        when(tracerBrave.currentSpan()).thenReturn(spanBrave);
        when(tracerBrave.newChild(any())).thenReturn(spanBrave);
        when(tracingBrave.tracer()).thenReturn(tracerBrave);
        NatsOpenCDXMessageServiceImpl service = new NatsOpenCDXMessageServiceImpl(
                this.connection, this.objectMapper, "test", openCDXCurrentUser, tracer);

        Assertions.assertDoesNotThrow(() -> service.send("subject", "ObjectTest"));
        //        try (MockedStatic<Tracing> tracingMockedStatic = Mockito.mockStatic(Tracing.class)) {
        //            tracingMockedStatic.when(Tracing::currentTracer).thenReturn(tracerBrave);
        //            tracingMockedStatic.when(Tracing::current).thenReturn(tracingBrave);
        //            when(connection.jetStream()).thenThrow(IOException.class);
        //            NatsOpenCDXMessageServiceImpl service =
        //                    new NatsOpenCDXMessageServiceImpl(this.connection, this.objectMapper, "test",
        // openCDXCurrentUser);
        //
        //            Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> service.send("subject", "ObjectTest"));
        //        }
    }
}
