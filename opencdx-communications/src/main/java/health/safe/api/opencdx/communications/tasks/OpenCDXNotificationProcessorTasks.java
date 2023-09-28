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
package health.safe.api.opencdx.communications.tasks;

import cdx.open_communication.v2alpha.NotificationPriority;
import cdx.open_communication.v2alpha.NotificationStatus;
import health.safe.api.opencdx.communications.repository.OpenCDXNotificaitonRepository;
import health.safe.api.opencdx.communications.service.OpenCDXCommunicationService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service task for running schedule tasks for processing Notifications in the queue.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXNotificationProcessorTasks {
    private static final String PROCESSING_NOTIFICATION = "Processing Notification: {}";
    private OpenCDXCommunicationService openCDXCommunicationService;
    private OpenCDXNotificaitonRepository openCDXNotificaitonRepository;

    /**
     * Constructor with the OpenCDXCommunicationService class used to send notifications
     *
     * @param openCDXCommunicationService Communication Service to use.
     * @param openCDXNotificaitonRepository Repository used to lookup notifications.
     */
    public OpenCDXNotificationProcessorTasks(
            OpenCDXCommunicationService openCDXCommunicationService,
            OpenCDXNotificaitonRepository openCDXNotificaitonRepository) {
        this.openCDXCommunicationService = openCDXCommunicationService;
        this.openCDXNotificaitonRepository = openCDXNotificaitonRepository;
    }

    @Scheduled(cron = "${queue.priority.high}")
    public void highPriorityNotifications() {
        log.info("Starting High Priority Notifications Processing");

        this.openCDXNotificaitonRepository
                .findAllByPriorityAndEmailStatusOrderByTimestampAsc(
                        NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE,
                        NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .forEach(notification -> {
                    log.info(PROCESSING_NOTIFICATION, notification.getId());
                    this.openCDXCommunicationService.processOpenCDXNotification(notification);
                });

        this.openCDXNotificaitonRepository
                .findAllByPriorityAndSmsStatusOrderByTimestampAsc(
                        NotificationPriority.NOTIFICATION_PRIORITY_IMMEDIATE,
                        NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .forEach(notification -> {
                    log.info(PROCESSING_NOTIFICATION, notification.getId());
                    this.openCDXCommunicationService.processOpenCDXNotification(notification);
                });

        this.openCDXNotificaitonRepository
                .findAllByPriorityAndEmailStatusOrderByTimestampAsc(
                        NotificationPriority.NOTIFICATION_PRIORITY_HIGH, NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .forEach(notification -> {
                    log.info(PROCESSING_NOTIFICATION, notification.getId());
                    this.openCDXCommunicationService.processOpenCDXNotification(notification);
                });

        this.openCDXNotificaitonRepository
                .findAllByPriorityAndSmsStatusOrderByTimestampAsc(
                        NotificationPriority.NOTIFICATION_PRIORITY_HIGH, NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .forEach(notification -> {
                    log.info(PROCESSING_NOTIFICATION, notification.getId());
                    this.openCDXCommunicationService.processOpenCDXNotification(notification);
                });

        log.info("Completed High Priority Notifications Processing");
    }

    @Scheduled(cron = "${queue.priority.medium}")
    public void mediumPriorityNotifications() {
        log.info("Starting Medium Priority Notifications Processing");

        this.openCDXNotificaitonRepository
                .findAllByPriorityAndEmailStatusOrderByTimestampAsc(
                        NotificationPriority.NOTIFICATION_PRIORITY_MEDIUM,
                        NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .forEach(notification -> {
                    log.info(PROCESSING_NOTIFICATION, notification.getId());
                    this.openCDXCommunicationService.processOpenCDXNotification(notification);
                });

        this.openCDXNotificaitonRepository
                .findAllByPriorityAndSmsStatusOrderByTimestampAsc(
                        NotificationPriority.NOTIFICATION_PRIORITY_MEDIUM,
                        NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .forEach(notification -> {
                    log.info(PROCESSING_NOTIFICATION, notification.getId());
                    this.openCDXCommunicationService.processOpenCDXNotification(notification);
                });

        log.info("Completed Medium Priority Notifications Processing");
    }

    @Scheduled(cron = "${queue.priority.low}")
    public void lowPriorityNotifications() {
        log.info("Starting Low Priority Notifications Processing");

        this.openCDXNotificaitonRepository
                .findAllByPriorityAndEmailStatusOrderByTimestampAsc(
                        NotificationPriority.NOTIFICATION_PRIORITY_LOW, NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .forEach(notification -> {
                    log.info(PROCESSING_NOTIFICATION, notification.getId());
                    this.openCDXCommunicationService.processOpenCDXNotification(notification);
                });

        this.openCDXNotificaitonRepository
                .findAllByPriorityAndSmsStatusOrderByTimestampAsc(
                        NotificationPriority.NOTIFICATION_PRIORITY_LOW, NotificationStatus.NOTIFICATION_STATUS_PENDING)
                .forEach(notification -> {
                    log.info(PROCESSING_NOTIFICATION, notification.getId());
                    this.openCDXCommunicationService.processOpenCDXNotification(notification);
                });

        log.info("Completed Low Priority Notifications Processing");
    }
}
