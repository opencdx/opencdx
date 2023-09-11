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
package health.safe.api.opencdx.commons.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import health.safe.api.opencdx.commons.dto.StatusMessage;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.handlers.OpenCDXMessageHandler;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.Subscription;
import io.nats.client.impl.Headers;
import io.nats.client.impl.NatsJetStreamMetaData;
import io.nats.client.support.Status;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

class NatsOpenCDXMessageServiceImplTest {

    @Mock
    Connection connection;

    @Mock
    Dispatcher dispatcher;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.connection = Mockito.mock(Connection.class);
        this.dispatcher = Mockito.mock(Dispatcher.class);

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new ProtobufModule());

        Mockito.when(this.connection.createDispatcher()).thenReturn(this.dispatcher);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void subscribe() {
        NatsOpenCDXMessageServiceImpl service = new NatsOpenCDXMessageServiceImpl(this.connection, this.objectMapper);

        OpenCDXMessageHandler handler = new OpenCDXMessageHandler() {
            @Override
            public void receivedMessage(byte[] message) {
                Assertions.assertEquals("Test", new String(message));
            }
        };

        final NatsOpenCDXMessageServiceImpl.NatsMessageHandler[] natsHandler =
                new NatsOpenCDXMessageServiceImpl.NatsMessageHandler[1];

        Mockito.when(this.dispatcher.subscribe(
                        Mockito.eq("Test-Message"), Mockito.any(NatsOpenCDXMessageServiceImpl.NatsMessageHandler.class)))
                .thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        System.out.println("Test: " + invocation.getArgument(1).toString());
                        natsHandler[0] = invocation.getArgument(1);
                        return null;
                    }
                });

        service.subscribe("Test-Message", handler);

        Assertions.assertDoesNotThrow(() -> natsHandler[0].onMessage(this.getMessage()));
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

    @Test
    void unSubscribe() {
        NatsOpenCDXMessageServiceImpl service = new NatsOpenCDXMessageServiceImpl(this.connection, this.objectMapper);
        Mockito.when(this.dispatcher.unsubscribe("TEST-MESSAGE")).thenReturn(this.dispatcher);
        Assertions.assertDoesNotThrow(() -> {
            service.unSubscribe("TEST-MESSAGE");
        });
    }

    @Test
    void send() {
        NatsOpenCDXMessageServiceImpl service = new NatsOpenCDXMessageServiceImpl(this.connection, this.objectMapper);
        Assertions.assertDoesNotThrow(() -> {
            service.send("TEST-MESSAGE", new StatusMessage());
        });
    }

    @Test
    void sendException() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        NatsOpenCDXMessageServiceImpl service = new NatsOpenCDXMessageServiceImpl(this.connection, mapper);
        Mockito.when(mapper.writeValueAsBytes(Mockito.any())).thenThrow(JsonProcessingException.class);

        StatusMessage message = new StatusMessage();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> {
            service.send("TEST-MESSAGE", message);
        });
    }
}
