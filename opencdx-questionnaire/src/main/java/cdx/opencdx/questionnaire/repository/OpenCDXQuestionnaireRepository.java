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
package cdx.opencdx.questionnaire.repository;

import cdx.opencdx.commons.data.OpenCDXRepository;
import cdx.opencdx.questionnaire.model.OpenCDXQuestionnaireModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The OpenCDXQuestionnaireRepository interface is responsible for managing and accessing
 * OpenCDXQuestionnaireModel objects. It extends the MongoRepository interface, which provides
 * basic CRUD operations for MongoDB.
 * <p>
 * This interface does not define any additional methods as it inherits all the necessary
 * methods from the MongoRepository interface. The OpenCDXQuestionnaireRepository interface
 * uses the OpenCDXQuestionnaireModel class as the entity type and ObjectId as the identifier
 * type for MongoDB.
 * <p>
 * It should be noted that this interface does not provide any example code, and does not
 * contain the @author and @version tags.
 */
public interface OpenCDXQuestionnaireRepository extends OpenCDXRepository<OpenCDXQuestionnaireModel> {}
