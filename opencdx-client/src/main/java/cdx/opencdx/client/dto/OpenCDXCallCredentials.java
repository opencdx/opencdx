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
package cdx.opencdx.client.dto;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;

/**
 * gRPC Credentials to use for gRPC Clients
 */
@Slf4j
public class OpenCDXCallCredentials extends CallCredentials {

    private String token;

    /**
     * Contrustor taking the token to use.
     * @param token String containing the token.
     */
    public OpenCDXCallCredentials(String token) {
        this.token = token;
    }

    @Override
    @SuppressWarnings("java:S1181")
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                headers.put(Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER), "Bearer " + token);
                metadataApplier.apply(headers);
            } catch (Throwable e) {
                metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        // noop
    }
}
