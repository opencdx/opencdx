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

import brave.Tracer;
import brave.Tracing;
import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.utils.CurrentUserHelper;
import com.mongodb.ReadPreference;
import com.mongodb.client.result.DeleteResult;
import io.micrometer.observation.annotation.Observed;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * OpenCDX MongoAudit to ensure Creator/Created and Modifier/Modified are set on each and every
 * record sent to Mongo.
 */
@Slf4j
@Observed(name = "opencdx")
@ExcludeFromJacocoGeneratedReport
@SuppressWarnings("java:S1181")
public class OpenCDXMongoAuditTemplate extends MongoTemplate {

    public static final String MONGO = "mongo.";
    public static final String MONGODB = "mongodb";
    public static final String MONGODB_COLLECTION = "mongodb.collection";
    public static final String MONGODB_COMMAND = "mongodb.command";
    private final Tracing tracing;
    /**
     * Constructor for MongoTemplate
     *
     * @param mongoDbFactory     MongoDbFactory to use to generate this MongoTemplate.
     * @param mongoConverter     MongoConverter to use for this MongoTemplate.
     */
    public OpenCDXMongoAuditTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter mongoConverter) {
        super(mongoDbFactory, mongoConverter);

        this.tracing = Tracing.current();
    }

    @Override
    public Document executeCommand(String jsonCommand) {
        // Start a new trace for each MongoDB operation
        brave.Span span = tracing.tracer().nextSpan();
        span.remoteServiceName(MONGODB);
        span.name(MONGO + this.getDb().getName());
        span.tag(MONGODB_COMMAND, jsonCommand);
        span.kind(brave.Span.Kind.CLIENT);
        span.start();

        try (Tracer.SpanInScope ws = tracing.tracer().withSpanInScope(span)) {
            return super.executeCommand(jsonCommand);
        } catch (Throwable e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    public Document executeCommand(Document command) {
        // Start a new trace for each MongoDB operation
        brave.Span span = tracing.tracer().nextSpan();
        span.remoteServiceName(MONGODB);
        span.name(MONGO + this.getDb().getName());
        span.tag(MONGODB_COMMAND, command.toJson());
        span.kind(brave.Span.Kind.CLIENT);
        span.start();

        try (Tracer.SpanInScope ws = tracing.tracer().withSpanInScope(span)) {
            return super.executeCommand(command);
        } catch (Throwable e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    public Document executeCommand(Document command, ReadPreference readPreference) {
        // Start a new trace for each MongoDB operation
        brave.Span span = tracing.tracer().nextSpan();
        span.remoteServiceName(MONGODB);
        span.name(MONGO + this.getDb().getName());
        span.tag(MONGODB_COMMAND, command.toJson());
        span.kind(brave.Span.Kind.CLIENT);
        span.start();

        try (Tracer.SpanInScope ws = tracing.tracer().withSpanInScope(span)) {
            return super.executeCommand(command, readPreference);
        } catch (Throwable e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    public void executeQuery(Query query, String collectionName, DocumentCallbackHandler dch) {
        // Start a new trace for each MongoDB operation
        brave.Span span = tracing.tracer().nextSpan();
        span.remoteServiceName(MONGODB);
        span.tag(MONGODB_COLLECTION, collectionName);
        span.name(MONGO + this.getDb().getName());
        span.tag("mongodb.query", query.toString());
        span.kind(brave.Span.Kind.CLIENT);
        span.start();

        try (Tracer.SpanInScope ws = tracing.tracer().withSpanInScope(span)) {
            super.executeQuery(query, collectionName, dch);
        } catch (Throwable e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    protected void executeQuery(
            Query query,
            String collectionName,
            DocumentCallbackHandler documentCallbackHandler,
            CursorPreparer preparer) {
        // Start a new trace for each MongoDB operation
        brave.Span span = tracing.tracer().nextSpan();
        span.remoteServiceName(MONGODB);
        span.tag(MONGODB_COLLECTION, collectionName);
        span.name(MONGO + this.getDb().getName());
        span.tag("mongodb.query", query.toString());
        span.kind(brave.Span.Kind.CLIENT);
        span.start();

        try (Tracer.SpanInScope ws = tracing.tracer().withSpanInScope(span)) {
            super.executeQuery(query, collectionName, documentCallbackHandler, preparer);
        } catch (Throwable e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    public <T> T execute(DbCallback<T> action) {
        // Start a new trace for each MongoDB operation
        brave.Span span = tracing.tracer().nextSpan();
        span.remoteServiceName(MONGODB);
        span.name(MONGO + this.getDb().getName());
        span.tag("mongodb.action", action.toString());
        span.kind(brave.Span.Kind.CLIENT);
        span.start();

        try (Tracer.SpanInScope ws = tracing.tracer().withSpanInScope(span)) {
            return super.execute(action);
        } catch (Throwable e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    public <T> T execute(Class<?> entityClass, CollectionCallback<T> callback) {
        // Start a new trace for each MongoDB operation
        brave.Span span = tracing.tracer().nextSpan();
        span.remoteServiceName(MONGODB);
        span.name(MONGO + this.getDb().getName());
        span.tag("mongodb.entityClass", entityClass.getName());
        span.tag("mongodb.callback", callback.toString());
        span.kind(brave.Span.Kind.CLIENT);
        span.start();

        try (Tracer.SpanInScope ws = tracing.tracer().withSpanInScope(span)) {
            return super.execute(entityClass, callback);
        } catch (Throwable e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    public <T> T execute(String collectionName, CollectionCallback<T> callback) {
        // Start a new trace for each MongoDB operation
        brave.Span span = tracing.tracer().nextSpan();
        span.remoteServiceName(MONGODB);
        span.tag(MONGODB_COLLECTION, collectionName);
        span.tag("mongodb.callback", callback.toString());
        span.name(MONGO + this.getDb().getName());
        span.kind(brave.Span.Kind.CLIENT);
        span.start();

        try (Tracer.SpanInScope ws = tracing.tracer().withSpanInScope(span)) {
            return super.execute(collectionName, callback);
        } catch (Throwable e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }

    @Override
    protected <T> T maybeCallBeforeSave(T object, Document document, String collection) {
        ObjectId identityID =
                CurrentUserHelper.getOpenCDXCurrentUser().getCurrentUser().getId();
        Instant date = Instant.now();

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
        ObjectId identityID =
                CurrentUserHelper.getOpenCDXCurrentUser().getCurrentUser().getId();
        Instant date = Instant.now();

        log.debug("Setting Modifier: {} modified: {}", identityID, date);

        Update update = new Update();
        update.set("modifier", identityID);
        update.set("modified", date);

        this.findAndModify(query, update, Document.class, collectionName);
    }
}
