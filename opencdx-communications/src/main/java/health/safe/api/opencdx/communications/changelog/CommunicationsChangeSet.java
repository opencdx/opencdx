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
package health.safe.api.opencdx.communications.changelog;

import cdx.open_communication.v2alpha.NotificationPriority;
import cdx.open_communication.v2alpha.TemplateType;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import health.safe.api.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import health.safe.api.opencdx.communications.model.OpenCDXEmailTemplateModel;
import health.safe.api.opencdx.communications.model.OpenCDXNotificationEventModel;
import health.safe.api.opencdx.communications.model.OpenCDXSMSTemplateModel;
import health.safe.api.opencdx.communications.repository.OpenCDXEmailTemplateRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXNotificationEventRepository;
import health.safe.api.opencdx.communications.repository.OpenCDXSMSTemplateRespository;
import java.util.List;
import org.bson.types.ObjectId;

@ChangeLog(order = "002")
@ExcludeFromJacocoGeneratedReport
public class CommunicationsChangeSet {

    public static final String SUBJECT = "subject";
    public static final String CONTENT = "content";
    public static final String MESSAGE = "message";
    public static final String VARIABLES_1 = "variables1";
    public static final String VARIABLES_2 = "variables2";
    public static final String EVENT_NAME = "eventName";
    public static final String EVENT_DESCRIPTION = "eventDescription";
    public static final String PARAMETERS_1 = "parameters1";
    public static final String PARAMETERS_2 = "parameters2";

    @ChangeSet(order = "001", id = "Create Welcome Template", author = "Gaurav Mishra")
    public void generateWelcomeTemplates(
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository) {
        OpenCDXEmailTemplateModel openCDXEmailTemplateModel = OpenCDXEmailTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d373a"))
                .templateType(TemplateType.TEMPLATE_TYPE_WELCOME)
                .subject(SUBJECT)
                .content(CONTENT)
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d373c"))
                .templateType(TemplateType.TEMPLATE_TYPE_WELCOME)
                .message(MESSAGE)
                .variables(List.of(VARIABLES_1, VARIABLES_2))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d373e"))
                .eventName(EVENT_NAME)
                .eventDescription(EVENT_DESCRIPTION)
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d373a"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d373c"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(List.of(PARAMETERS_1, PARAMETERS_2))
                .build();
        openCDXEmailTemplateRepository.save(openCDXEmailTemplateModel);
        openCDXSMSTemplateRespository.save(openCDXSMSTemplateModel);
        openCDXNotificationEventRepository.save(openCDXNotificationEventModel);
    }

    @ChangeSet(order = "002", id = "Create Newsletter Template", author = "Gaurav Mishra")
    public void generateNewsletterTemplate(
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository) {
        OpenCDXEmailTemplateModel openCDXEmailTemplateModel = OpenCDXEmailTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3741"))
                .templateType(TemplateType.TEMPLATE_TYPE_NEWSLETTER)
                .subject(SUBJECT)
                .content(CONTENT)
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3742"))
                .templateType(TemplateType.TEMPLATE_TYPE_NEWSLETTER)
                .message(MESSAGE)
                .variables(List.of(VARIABLES_1, VARIABLES_2))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3743"))
                .eventName(EVENT_NAME)
                .eventDescription(EVENT_DESCRIPTION)
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d3741"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d3742"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(List.of(PARAMETERS_1, PARAMETERS_2))
                .build();
        openCDXEmailTemplateRepository.save(openCDXEmailTemplateModel);
        openCDXSMSTemplateRespository.save(openCDXSMSTemplateModel);
        openCDXNotificationEventRepository.save(openCDXNotificationEventModel);
    }

    @ChangeSet(order = "003", id = "Create Alert Template", author = "Gaurav Mishra")
    public void generateAlertTemplate(
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository) {
        OpenCDXEmailTemplateModel openCDXEmailTemplateModel = OpenCDXEmailTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3744"))
                .templateType(TemplateType.TEMPLATE_TYPE_ALERT)
                .subject(SUBJECT)
                .content(CONTENT)
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3745"))
                .templateType(TemplateType.TEMPLATE_TYPE_ALERT)
                .message(MESSAGE)
                .variables(List.of(VARIABLES_1, VARIABLES_2))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3746"))
                .eventName(EVENT_NAME)
                .eventDescription(EVENT_DESCRIPTION)
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d3744"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d3745"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(List.of(PARAMETERS_1, PARAMETERS_2))
                .build();
        openCDXEmailTemplateRepository.save(openCDXEmailTemplateModel);
        openCDXSMSTemplateRespository.save(openCDXSMSTemplateModel);
        openCDXNotificationEventRepository.save(openCDXNotificationEventModel);
    }

    @ChangeSet(order = "004", id = "Create Reminder Template", author = "Gaurav Mishra")
    public void generateReminderTemplate(
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository) {
        OpenCDXEmailTemplateModel openCDXEmailTemplateModel = OpenCDXEmailTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3747"))
                .templateType(TemplateType.TEMPLATE_TYPE_REMINDER)
                .subject(SUBJECT)
                .content(CONTENT)
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3748"))
                .templateType(TemplateType.TEMPLATE_TYPE_REMINDER)
                .message(MESSAGE)
                .variables(List.of(VARIABLES_1, VARIABLES_2))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3749"))
                .eventName(EVENT_NAME)
                .eventDescription(EVENT_DESCRIPTION)
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d3747"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d3748"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(List.of(PARAMETERS_1, PARAMETERS_2))
                .build();
        openCDXEmailTemplateRepository.save(openCDXEmailTemplateModel);
        openCDXSMSTemplateRespository.save(openCDXSMSTemplateModel);
        openCDXNotificationEventRepository.save(openCDXNotificationEventModel);
    }

    @ChangeSet(order = "005", id = "Create Confirmation Template", author = "Gaurav Mishra")
    public void generateConfirmationTemplate(
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository) {
        OpenCDXEmailTemplateModel openCDXEmailTemplateModel = OpenCDXEmailTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d374a"))
                .templateType(TemplateType.TEMPLATE_TYPE_CONFIRMATION)
                .subject(SUBJECT)
                .content(CONTENT)
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d374b"))
                .templateType(TemplateType.TEMPLATE_TYPE_CONFIRMATION)
                .message(MESSAGE)
                .variables(List.of(VARIABLES_1, VARIABLES_2))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d374c"))
                .eventName(EVENT_NAME)
                .eventDescription(EVENT_DESCRIPTION)
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d374a"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d374b"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(List.of(PARAMETERS_1, PARAMETERS_2))
                .build();
        openCDXEmailTemplateRepository.save(openCDXEmailTemplateModel);
        openCDXSMSTemplateRespository.save(openCDXSMSTemplateModel);
        openCDXNotificationEventRepository.save(openCDXNotificationEventModel);
    }

    @ChangeSet(order = "006", id = "Create Notification Template", author = "Gaurav Mishra")
    public void generateNotificationTemplate(
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository) {
        OpenCDXEmailTemplateModel openCDXEmailTemplateModel = OpenCDXEmailTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d374e"))
                .templateType(TemplateType.TEMPLATE_TYPE_NOTIFICATION)
                .subject(SUBJECT)
                .content(CONTENT)
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d374f"))
                .templateType(TemplateType.TEMPLATE_TYPE_NOTIFICATION)
                .message(MESSAGE)
                .variables(List.of(VARIABLES_1, VARIABLES_2))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3750"))
                .eventName(EVENT_NAME)
                .eventDescription(EVENT_DESCRIPTION)
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d374e"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d374f"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(List.of(PARAMETERS_1, PARAMETERS_2))
                .build();
        openCDXEmailTemplateRepository.save(openCDXEmailTemplateModel);
        openCDXSMSTemplateRespository.save(openCDXSMSTemplateModel);
        openCDXNotificationEventRepository.save(openCDXNotificationEventModel);
    }
}
