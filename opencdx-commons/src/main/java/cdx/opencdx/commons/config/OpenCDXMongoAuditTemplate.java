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
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * OpenCDX MongoAudit to ensure Creator/Created and Modifier/Modified are set on each and every
 * record sent to Mongo.
 */
@Slf4j
@Observed(name = "opencdx")
@ExcludeFromJacocoGeneratedReport
@SuppressWarnings("java:S1181")
public class OpenCDXMongoAuditTemplate extends MongoTemplate {

    /**
     * Constructor for MongoTemplate
     *
     * @param mongoDbFactory     MongoDbFactory to use to generate this MongoTemplate.
     * @param mongoConverter     MongoConverter to use for this MongoTemplate.
     */
    public OpenCDXMongoAuditTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter mongoConverter) {
        super(mongoDbFactory, mongoConverter);
        log.trace("OpenCDXMongoAuditTemplate created");
    }

    @Override
    protected <T> T maybeCallBeforeConvert(T object, String collection) {
        boolean hasSetIdMethod = hasMethod(object.getClass(), "setId");
        boolean hasGetMethod = hasMethod(object.getClass(), "getId");

        try {
            if (hasGetMethod && hasSetIdMethod) {
                Method getIdMethod = object.getClass().getMethod("getId");
                OpenCDXIdentifier id = (OpenCDXIdentifier) getIdMethod.invoke(object);

                if (id == null) {
                    Method setIdMethod = object.getClass().getMethod("setId", OpenCDXIdentifier.class);
                    setIdMethod.invoke(object, new OpenCDXIdentifier());
                }
            }
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            log.error("Failed to add OpenCDXIdentifier", e);
        }

        return super.maybeCallBeforeConvert(object, collection);
    }

    // Method to check if a class has a specific method
    private static boolean hasMethod(Class<?> clazz, String methodName) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }
}
