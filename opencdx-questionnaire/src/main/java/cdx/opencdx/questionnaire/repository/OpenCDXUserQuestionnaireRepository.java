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

import cdx.opencdx.questionnaire.model.OpenCDXUserQuestionnaireModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The OpenCDXUserQuestionnaireRepository interface provides methods for interacting with the "questionnaire-user" collection in MongoDB.
 * This interface extends the MongoRepository interface, which provides basic CRUD operations.
 */
public interface OpenCDXUserQuestionnaireRepository extends MongoRepository<OpenCDXUserQuestionnaireModel, ObjectId> {}
