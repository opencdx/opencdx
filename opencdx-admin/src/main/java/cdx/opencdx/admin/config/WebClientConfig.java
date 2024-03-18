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
package cdx.opencdx.admin.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinWebClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

/**
 * The WebClientConfig class provides configuration for the WebClient in a Spring application.
 * It creates a custom HttpClient with a custom SSLContext and returns a ClientHttpConnector
 * bean that can be used to configure and create a WebClient instance.
 */
@Slf4j
@Configuration
public class WebClientConfig {
    /**
     * Default constructor for WebClientConfig class.
     * This constructor allows creating an instance of WebClientConfig without providing any initial values.
     */
    public WebClientConfig() {
        // Default constructor body (usually empty in a configuration class)
    }

    /**
     * Returns a custom {@link ClientHttpConnector} bean that can be used to configure and create a {@link HttpClient} instance.
     * The custom {@link ClientHttpConnector} uses a custom {@link HttpClient} with a custom SSLContext.
     *
     * @return The custom {@link ClientHttpConnector} bean.
     * @throws SSLException If an SSL error occurs while creating the SSLContext.
     */
    @Bean
    public ClientHttpConnector customHttpClient() throws SSLException {
        return createHttpConnector();
    }

    /**
     * Customizer for WebClient
     * @return ZipkinWebClientBuilderCustomizer
     */
    @Bean
    ZipkinWebClientBuilderCustomizer myCustomizer() {
        return webClientBuilder -> {
            try {
                webClientBuilder
                        .clientConnector(createHttpConnector())
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            } catch (SSLException e) {
                log.error("Error creating SSL context", e);
            }
        };
    }

    private ClientHttpConnector createHttpConnector() throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(ssl -> ssl.sslContext(sslContext));
        return new ReactorClientHttpConnector(httpClient);
    }
}
