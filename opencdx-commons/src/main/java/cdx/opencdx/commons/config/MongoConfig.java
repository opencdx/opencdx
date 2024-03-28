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

import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.converters.OpenCDXIdentifierCodec;
import cdx.opencdx.commons.converters.OpenCDXIdentifierReadConverter;
import cdx.opencdx.commons.converters.OpenCDXIdentifierWriteConverter;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

/**
 * Configuration for MongoDB.
 */
@Profile("mongo")
@Description("MongoTemplate to use with Creator/created and Modifier/modified values set.")
@ExcludeFromJacocoGeneratedReport
@Slf4j
@AutoConfiguration
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String db;

    /**
     * Default Constructor.
     */
    public MongoConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    private CodecRegistry codecRegistry() {
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        Codec<OpenCDXIdentifier> openCDXIdentifierCodec = new OpenCDXIdentifierCodec(defaultCodecRegistry);
        return fromRegistries(defaultCodecRegistry, fromCodecs(openCDXIdentifierCodec));
    }

    @Override
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(this.codecRegistry())
                .applyConnectionString(new ConnectionString(uri))
                .build();

        return MongoClients.create(settings);
    }

    @Override
    protected String getDatabaseName() {
        return db;
    }

    @Override
    protected void configureConverters(
            MongoCustomConversions.MongoConverterConfigurationAdapter converterConfigurationAdapter) {
        converterConfigurationAdapter.registerConverter(new OpenCDXIdentifierReadConverter());
        converterConfigurationAdapter.registerConverter(new OpenCDXIdentifierWriteConverter());
    }

    @Override
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory, MappingMongoConverter converter) {
        log.trace("Creating Mongo Template");
        return new OpenCDXMongoAuditTemplate(databaseFactory, converter);
    }
}
