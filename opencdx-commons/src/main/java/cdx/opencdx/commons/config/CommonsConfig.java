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
import cdx.opencdx.commons.cache.OpenCDXMemoryCacheManager;
import cdx.opencdx.commons.handlers.OpenCDXPerformanceHandler;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.*;
import cdx.opencdx.commons.service.impl.*;
import cdx.opencdx.commons.utils.MongoDocumentExists;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcServerInterceptor;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.micrometer.tracing.Tracer;
import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.observability.ContextProviderFactory;
import org.springframework.data.mongodb.observability.MongoObservationCommandListener;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Autoconfiguraiton class for opencdx-commons.
 */
@Slf4j
@EnableCaching
@AutoConfiguration
@Configuration
public class CommonsConfig {
    /**
     * Default Constructor
     */
    public CommonsConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Bean
    public ConnectionListener createConnectionListener() {
        return (conn, type) -> log.error(
                "Connection Event: {}  Connection: {}", type, conn.getStatus().name());
    }

    @Bean
    @ExcludeFromJacocoGeneratedReport
    ObservationRegistryCustomizer<ObservationRegistry> skipActuatorEndpointsFromObservation() {
        log.trace("Setting up Observation Registry");
        PathMatcher pathMatcher = new AntPathMatcher("/");
        return registry -> registry.observationConfig()
                .observationPredicate((name, context) -> observationPrediction(context, pathMatcher));
    }

    @ExcludeFromJacocoGeneratedReport
    private static boolean observationPrediction(Observation.Context context, PathMatcher pathMatcher) {
        log.trace("Observation Prediction: {}", context);
        if (context instanceof ServerRequestObservationContext observationContext) {
            return !pathMatcher.match(
                    "/actuator/**", observationContext.getCarrier().getRequestURI());
        } else {
            return true;
        }
    }

    /**
     * Observation Registry
     * @param observationRegistry Observation Registry
     * @return Observation Registry
     */
    @Bean
    @GRpcGlobalInterceptor
    @ExcludeFromJacocoGeneratedReport
    public ObservationGrpcServerInterceptor interceptor(ObservationRegistry observationRegistry) {
        log.trace("Setting up gRPC Server Interceptor");
        return new ObservationGrpcServerInterceptor(observationRegistry);
    }

    @Bean
    @Profile("mongo")
    @ExcludeFromJacocoGeneratedReport
    MongoClientSettingsBuilderCustomizer mongoObservabilityCustomizer(
            ObservationRegistry observationRegistry, MongoProperties mongoProperties) {
        log.trace("Setting up Mongo Observability Customizer");
        return clientSettingsBuilder ->
                getClientSetttingsBuilder(observationRegistry, mongoProperties, clientSettingsBuilder);
    }

    @ExcludeFromJacocoGeneratedReport
    private static MongoClientSettings.Builder getClientSetttingsBuilder(
            ObservationRegistry observationRegistry,
            MongoProperties mongoProperties,
            MongoClientSettings.Builder clientSettingsBuilder) {
        log.trace("Setting up Mongo Observability Customizer");
        return clientSettingsBuilder
                .contextProvider(ContextProviderFactory.create(observationRegistry))
                .addCommandListener(new MongoObservationCommandListener(
                        observationRegistry, new ConnectionString(mongoProperties.determineUri())));
    }

    /**
     * Default Cache Manager
     * @return OpenCDXMemoryCacheManager
     */
    @Bean
    @Primary
    public CacheManager cacheManager() {
        log.trace("Setting up Cache Manager");
        return new OpenCDXMemoryCacheManager();
    }

    /**
     * Default Password Encoder
     * @return Delegating password Encoder
     */
    @Bean
    @Primary
    @Description("Password Encoder using the recommend Spring Delegating encoder.")
    public PasswordEncoder passwordEncoder() {
        log.trace("Setting up Password Encoder");
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * OpenCDXHtmlSanitizer
     * @return OpenCDXHtmlSanitizer for use by the system.
     */
    @Bean
    @Primary
    @Description("OWASP Html Sanitizer.")
    public OpenCDXHtmlSanitizer sanitizer() {
        log.trace("Setting up OpenCDXHtmlSanitizer");
        return new OwaspHtmlSanitizerImpl();
    }

    /**
     * Observation Registry for the OpenCDX Performance Tracking.
     * @param observationRegistry Registry
     * @return ObservedAspect with performance handler.
     */
    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        log.trace("Setting up ObservedAspect for Observability");

        observationRegistry.observationConfig().observationHandler(new OpenCDXPerformanceHandler());
        return new ObservedAspect(observationRegistry);
    }

    /**
     * Generates the Jackson Objectmapper with the JSON-to/from-Protobuf Message support.
     * @return ObjectMapper bean for use.
     */
    @Bean
    @Primary
    @Description("Jackson ObjectMapper with all required registered modules.")
    public ObjectMapper objectMapper() {
        log.trace("Creating ObjectMapper for use by system");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new ProtobufClassAttributesModule());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    /**
     * OpenCDXMessageService Bean based on NATS
     * @param natsConnection NATS Connection
     * @param objectMapper Object Mapper to use.
     * @param applicationName Name of the service.
     * @param openCDXCurrentUser Current User Service
     * @param tracer Tracer to use.
     * @return OpenCDXMessageService to use for messaginging.
     */
    @Bean("nats")
    @Description("NATS implementation of the OpenCDXMessageService.")
    @Primary
    @ConditionalOnProperty(prefix = "nats.spring", name = "server")
    public OpenCDXMessageService natsOpenCDXMessageService(
            Connection natsConnection,
            ObjectMapper objectMapper,
            OpenCDXCurrentUser openCDXCurrentUser,
            @Value("${spring.application.name}") String applicationName,
            Tracer tracer) {
        log.trace("Using NATS based Messaging Service");
        return new NatsOpenCDXMessageServiceImpl(
                natsConnection, objectMapper, applicationName, openCDXCurrentUser, tracer);
    }

    @Bean("noop")
    @Description("The NOOP implementation of the OpenCDXMessage Service.")
    @ConditionalOnMissingBean(OpenCDXMessageService.class)
    OpenCDXMessageService noOpOpenCDXMessageService() {
        log.trace("Using NOOP based messaging service.");
        return new NoOpOpenCDXMessageServiceImpl();
    }

    @Bean
    @Description("OpenCDXOpenCDXAuditService for submitting audit messages through the message system.")
    OpenCDXAuditService openCDXAuditService(
            OpenCDXMessageService messageService,
            @Value("${spring.application.name}") String applicationName,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        log.trace("Creaging Audit Service for {}", applicationName);
        return new OpenCDXAuditServiceImpl(messageService, applicationName, openCDXDocumentValidator);
    }

    @Bean
    @Primary
    @Profile("mongo")
    @Description("MongoTemplate to use with Creator/created and Modifier/modified values set.")
    @ExcludeFromJacocoGeneratedReport
    MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter mongoConverter) {
        log.trace("Creating Mongo Template");
        return new OpenCDXMongoAuditTemplate(mongoDbFactory, mongoConverter);
    }

    @Bean
    @Primary
    @Profile("test")
    OpenCDXDocumentValidator noOpDocumentValidator() {
        log.trace("Creating NoOp Document Validator");
        return new NoOpDocumentValidatorImpl();
    }

    @Bean
    @Profile("!test")
    @ExcludeFromJacocoGeneratedReport
    @ConditionalOnMissingBean(MongoDocumentExists.class)
    MongoDocumentExists mongoDocumentExists(MongoTemplate mongoTemplate) {
        log.trace("Creating Mongo Document Exists");
        return new MongoDocumentExists(mongoTemplate);
    }

    @Bean
    @Profile("!test")
    @ExcludeFromJacocoGeneratedReport
    @ConditionalOnMissingBean(OpenCDXDocumentValidator.class)
    OpenCDXDocumentValidator mongoDocumentValidatorImpl(
            MongoTemplate mongoTemplate,
            MongoDocumentExists mongoDocumentExists,
            OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        log.trace("Creating Mongo Document Validator");
        return new MongoDocumentValidatorImpl(mongoTemplate, mongoDocumentExists, openCDXIAMUserRepository);
    }

    /**
     * Model Resolver for Swagger
     * @param objectMapper Object Mapper to use.
     * @return Model Resolver for Swagger
     */
    @Bean
    public ModelResolver modelResolver(final ObjectMapper objectMapper) {
        log.trace("Creating Model Resolver for Swagger");
        return new ModelResolver(objectMapper);
    }

    /**
     * OpenAPI Configuration
     * @return OpenAPI Configuration
     */
    @Bean
    public OpenAPI customizeOpenAPI() {
        log.trace("Customizing OpenAPI Configuration");
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(
                                securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
