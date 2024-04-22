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
package cdx.opencdx.commons.config;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinWebClientBuilderCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Configuration for WebClient
 */
@ExcludeFromJacocoGeneratedReport
@AutoConfiguration
@Configuration
@Slf4j
public class WebClientConfig {

    /**
     * Customizer for WebClient
     */
    public WebClientConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Customizer for WebClient
     * @return ZipkinWebClientBuilderCustomizer
     */
    @Bean
    @ExcludeFromJacocoGeneratedReport
    ZipkinWebClientBuilderCustomizer myCustomizer(@Value("${opencdx.client.trustStore}") String trustStore) {
        return webClientBuilder -> {
            final HttpClient httpClient = HttpClient.create().secure(ssl -> {
                try (InputStream certChain = new FileInputStream(trustStore)) {
                    ssl.sslContext(SslContextBuilder.forClient()
                            .trustManager(certChain)
                            .build());
                } catch (IOException e) {
                    throw new OpenCDXInternal(this.getClass().getName(), 1, "SSL Exception", e);
                }
            });
            webClientBuilder
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        };
    }
}
