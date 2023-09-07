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
package health.safe.api.opencdx.commons.services;

import io.grpc.Server;
import io.grpc.inprocess.InProcessServerBuilder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * InProcessServer that manages startup/shutdown of a service within the same process as the client is running. Used for unit testing purposes.
 * @author be
 */
@Slf4j
public class InProcessService<T extends io.grpc.BindableService> {

    private Server server;

    private Class<T> clazz;

    public InProcessService(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void start() throws IOException, InstantiationException, IllegalAccessException {
        server = InProcessServerBuilder.forName("test")
                .directExecutor()
                .addService(clazz.newInstance())
                .build()
                .start();
        log.info("InProcessService started.");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                log.error("*** shutting down gRPC server since JVM is shutting down");
                InProcessService.this.stop();
                log.error("*** server shut down");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
