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
package health.safe.api.opencdx.commons.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.result.DeleteResult;
import health.safe.api.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import health.safe.api.opencdx.commons.aspects.AuditAspect;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * OpenCDX MongoAudit to ensure Creator/Created and Modifier/Modified are set on each and every
 * record sent to Mongo.
 */
@Slf4j
@ExcludeFromJacocoGeneratedReport
public class OpenCDXMongoAuditTemplate extends MongoTemplate {
    AuditAspect auditAspect = new AuditAspect();

    /**
     * Constructor  for MongoTemplate
     * @param mongoClient MongoClient to use.
     * @param databaseName Database name to used
     */
    public OpenCDXMongoAuditTemplate(MongoClient mongoClient, String databaseName) {
        super(mongoClient, databaseName);
    }

    /**
     * Constructor for MongoTemplate
     * @param mongoDbFactory MongoDbFactory to use to generate this MongoTemplate.
     */
    public OpenCDXMongoAuditTemplate(MongoDatabaseFactory mongoDbFactory) {
        super(mongoDbFactory);
    }

    /**
     * Constructor for MongoTemplate
     * @param mongoDbFactory  MongoDbFactory to use to generate this MongoTemplate.
     * @param mongoConverter MongoConverter to use for this MongoTemplate.
     */
    public OpenCDXMongoAuditTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter mongoConverter) {
        super(mongoDbFactory, mongoConverter);
    }

    @Override
    protected <T> T maybeCallBeforeSave(T object, Document document, String collection) {
        RequestActorAttributes requestActorAttributes = AuditAspect.getCurrentThreadInfo();
        String identityID = "system";
        Date date = new Date();

        if (requestActorAttributes != null) {
            identityID = requestActorAttributes.getActor();
        }

        Object id = document.get("_id");
        Object creator = document.get("creator");
        if (id == null || creator == null) {
            log.debug("Setting Creator: {} created: {}", identityID, date);
            document.put("creator", identityID);
            document.put("created", date);
        }
        log.debug("Setting Modifier: {} modified: {}", identityID, date);
        document.put("modifier", identityID);
        document.put("modified", date);

        return super.maybeCallBeforeSave(object, document, collection);
    }

    @Override
    public <T> T findAndRemove(Query query, Class<T> entityClass, String collectionName) {
        updateRemovalQuery(collectionName, query);
        return super.findAndRemove(query, entityClass, collectionName);
    }

    @Override
    public <T> List<T> findAllAndRemove(Query query, Class<T> entityClass, String collectionName) {
        updateRemovalQuery(collectionName, query);
        return super.findAllAndRemove(query, entityClass, collectionName);
    }

    @Override
    protected <T> DeleteResult doRemove(String collectionName, Query query, Class<T> entityClass, boolean multi) {
        updateRemovalQuery(collectionName, query);
        return super.doRemove(collectionName, query, entityClass, multi);
    }

    private void updateRemovalQuery(String collectionName, Query query) {
        RequestActorAttributes requestActorAttributes = AuditAspect.getCurrentThreadInfo();
        String identityID = "system";
        Date date = new Date();

        if (requestActorAttributes != null) {
            identityID = requestActorAttributes.getActor();
        }

        log.debug("Setting Modifier: {} modified: {}", identityID, date);

        Update update = new Update();
        update.set("modifier", identityID);
        update.set("modified", date);

        this.findAndModify(query, update, Document.class, collectionName);
    }
}
