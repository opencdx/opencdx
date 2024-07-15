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
package cdx.opencdx.communications.service;

import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.communications.model.OpenCDXNotificationModel;
import cdx.opencdx.grpc.data.Notification;
import cdx.opencdx.grpc.data.NotificationEvent;
import cdx.opencdx.grpc.service.communications.NotificationEventListRequest;
import cdx.opencdx.grpc.service.communications.NotificationEventListResponse;
import cdx.opencdx.grpc.service.communications.SuccessResponse;
import cdx.opencdx.grpc.service.communications.TemplateRequest;

/**
 * Interface for the OpenCDXNotificationService
 */
public interface OpenCDXNotificationService {

    /**
     * Create a Notification Event
     * @param notificationEvent NotificationEvent to create.
     * @return the created NotificationEvent.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    NotificationEvent createNotificationEvent(NotificationEvent notificationEvent) throws OpenCDXNotAcceptable;

    /**
     * Get a Notification Event
     * @param templateRequest Request ID of the NotificationEvent to retrieve.
     * @return the requested NotificationEvent.
     * @exception OpenCDXNotFound Template with requested ID not found.
     */
    NotificationEvent getNotificationEvent(TemplateRequest templateRequest)
            throws OpenCDXNotFound, OpenCDXNotAcceptable;

    /**
     * Update Notification Event
     * @param notificationEvent NotificationEvent to update.
     * @return the updated NotificationEvent.
     * @exception OpenCDXFailedPrecondition Missing event id.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    NotificationEvent updateNotificationEvent(NotificationEvent notificationEvent)
            throws OpenCDXFailedPrecondition, OpenCDXNotAcceptable;

    /**
     * Delete Notification Event
     * @param templateRequest Request ID of NotificationEvent to delete.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse deleteNotificationEvent(TemplateRequest templateRequest) throws OpenCDXNotAcceptable;

    /**
     * Send Notification
     * @param notification Notification information to trigger.
     * @return SuccessResponse indicating if the action was successful.
     * @exception OpenCDXNotFound Template not found
     * @exception OpenCDXFailedPrecondition Missing variable from data for substitution.
     * @exception OpenCDXNotAcceptable Failed to convert to JSON
     */
    SuccessResponse sendNotification(Notification notification)
            throws OpenCDXFailedPrecondition, OpenCDXNotFound, OpenCDXNotAcceptable;

    /**
     * List of all NotificationEvent
     * @param request Request indicating pagination, sorting, and page size.
     * @return requested NotificationEvent with page, sorting, and page size
     */
    NotificationEventListResponse listNotificationEvents(NotificationEventListRequest request);

    /**
     * Process and send the Notificaiton
     * @param openCDXNotificationModel OpenCDXNotificaitonModel to process.
     */
    void processOpenCDXNotification(OpenCDXNotificationModel openCDXNotificationModel);
}
