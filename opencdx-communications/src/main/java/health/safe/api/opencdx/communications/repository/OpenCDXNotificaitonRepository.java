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
package health.safe.api.opencdx.communications.repository;

import cdx.open_communication.v2alpha.NotificationPriority;
import cdx.open_communication.v2alpha.NotificationStatus;
import health.safe.api.opencdx.communications.model.OpenCDXNotificationModel;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoRepository for the notificaitons collection.
 */
@Repository
public interface OpenCDXNotificaitonRepository extends MongoRepository<OpenCDXNotificationModel, ObjectId> {
    /**
     * Find all Notificaitons with given priority and email status
     * @param priority NotificaitonPriority to search for
     * @param emailStatus The email status to search
     * @return List of notifications.
     */
    List<OpenCDXNotificationModel> findAllByPriorityAndEmailStatusOrderByTimestampAsc(
            NotificationPriority priority, NotificationStatus emailStatus);
    /**
     * Find all Notificaitons with given priority and sms status
     * @param priority NotificaitonPriority to search for
     * @param smsStatus The sms status to search
     * @return List of notifications.
     */
    List<OpenCDXNotificationModel> findAllByPriorityAndSmsStatusOrderByTimestampAsc(
            NotificationPriority priority, NotificationStatus smsStatus);

    /**
     * Indicates if an notification exists using for a given Event ID
     * @param id Id of the NotificationEvent to check for being used.
     * @return boolean indicating if found.
     */
    boolean existsByEventId(ObjectId id);
}
