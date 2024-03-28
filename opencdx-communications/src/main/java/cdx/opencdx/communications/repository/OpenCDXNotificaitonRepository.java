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
package cdx.opencdx.communications.repository;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.data.OpenCDXRepository;
import cdx.opencdx.communications.model.OpenCDXNotificationModel;
import cdx.opencdx.grpc.communication.*;
import io.micrometer.observation.annotation.Observed;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * MongoRepository for the notificaitons collection.
 */
@Repository
@Observed(name = "opencdx")
public interface OpenCDXNotificaitonRepository extends OpenCDXRepository<OpenCDXNotificationModel> {
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
    boolean existsByEventId(OpenCDXIdentifier id);
}
