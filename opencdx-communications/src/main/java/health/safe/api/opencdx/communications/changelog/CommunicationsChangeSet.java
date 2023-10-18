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
import java.util.Collections;
import java.util.List;
import org.bson.types.ObjectId;

@ChangeLog(order = "002")
@ExcludeFromJacocoGeneratedReport
public class CommunicationsChangeSet {

    public static final String MESSAGE = "message";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";

    @ChangeSet(order = "001", id = "Create Welcome Template", author = "Gaurav Mishra")
    public void generateWelcomeTemplates(
            OpenCDXEmailTemplateRepository openCDXEmailTemplateRepository,
            OpenCDXSMSTemplateRespository openCDXSMSTemplateRespository,
            OpenCDXNotificationEventRepository openCDXNotificationEventRepository) {
        OpenCDXEmailTemplateModel openCDXEmailTemplateModel = OpenCDXEmailTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d373a"))
                .templateType(TemplateType.TEMPLATE_TYPE_WELCOME)
                .subject("Welcome to OpenCDX")
                .content(
                        """
                        Welcome ${firstName} ${lastName},

                        Welcome to OpenCDX. Your account has been setup with your username: ${email}.

                        Thank you!
                        """)
                .variables(List.of(FIRST_NAME, LAST_NAME, "email"))
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d373c"))
                .templateType(TemplateType.TEMPLATE_TYPE_WELCOME)
                .message("Welcome ${firstName} ${lastName} to OpenCDX.  Your username is: ${email}.")
                .variables(List.of(FIRST_NAME, LAST_NAME, "email"))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d373e"))
                .eventName("Welcome to OpenCDX")
                .eventDescription("New Member has joined OpenCDX")
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d373a"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d373c"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(Collections.emptyList())
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
                .subject("OpenCDX Newsletter")
                .content("""
                        ${message}.
                        """)
                .variables(List.of(MESSAGE))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3743"))
                .eventName("OpenCDX Newsletter")
                .eventDescription("Periodic newsletter sent to user. ")
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d3741"))
                .emailRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(Collections.emptyList())
                .build();
        openCDXEmailTemplateRepository.save(openCDXEmailTemplateModel);
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
                .subject("OpenCDX Alert")
                .content(
                        """
                        Dear ${firstName} ${lastName},

                        Please be advised you are receiving this alert for the following reason: ${reason}.

                        Thank you!
                        """)
                .variables(List.of(FIRST_NAME, LAST_NAME, "reason"))
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3745"))
                .templateType(TemplateType.TEMPLATE_TYPE_ALERT)
                .message("Please be advised you are receiving this alert for the following reason: ${reason}.")
                .variables(List.of("reason"))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3746"))
                .eventName("Alert Notification to inform the user.")
                .eventDescription("Alert Notification to inform the user.")
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d3744"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d3745"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(Collections.emptyList())
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
                .subject("OpenCDX Reminder")
                .content(
                        """
                        Dear ${firstName} ${lastName},

                        Reminder you have ${reminder}.

                        Thank you!
                        """)
                .variables(List.of(FIRST_NAME, LAST_NAME, "reminder"))
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3748"))
                .templateType(TemplateType.TEMPLATE_TYPE_REMINDER)
                .message("Reminder: ${reminder}")
                .variables(List.of("reminder"))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3749"))
                .eventName("Reminder to User")
                .eventDescription("Notificaiton to user of upcoming event.")
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d3747"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d3748"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(Collections.emptyList())
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
                .subject("OpenCDX Confirmation")
                .content(
                        """
                        Dear ${firstName} ${lastName},

                        This is to confirm you have ${confirmation}. If this is not accurate
                        please contact OpenCDX immediately.

                        Thank you!
                        """)
                .variables(List.of(FIRST_NAME, LAST_NAME, "confirmation"))
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d374b"))
                .templateType(TemplateType.TEMPLATE_TYPE_CONFIRMATION)
                .message(
                        "This is to confirm you have ${confirmation}. If this is not accurate please contact OpenCDX immediately.")
                .variables(List.of("confirmation"))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d374c"))
                .eventName("Confirmation of activity")
                .eventDescription("Confirmation of an activity")
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d374a"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d374b"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .parameters(Collections.emptyList())
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
                .subject("OpenCDX Notification")
                .content(
                        """
                        Dear ${firstName} ${lastName},

                        This is to notify you of ${notification}.

                        Thank you!
                        """)
                .variables(List.of(FIRST_NAME, LAST_NAME, "notification"))
                .build();
        OpenCDXSMSTemplateModel openCDXSMSTemplateModel = OpenCDXSMSTemplateModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d374f"))
                .templateType(TemplateType.TEMPLATE_TYPE_NOTIFICATION)
                .message("${notification}")
                .variables(List.of("notification"))
                .build();
        OpenCDXNotificationEventModel openCDXNotificationEventModel = OpenCDXNotificationEventModel.builder()
                .id(new ObjectId("60f1e6b1f075a361a94d3750"))
                .eventName("Notification to user")
                .eventDescription("Notification to user of an event.")
                .emailTemplateId(new ObjectId("60f1e6b1f075a361a94d374e"))
                .emailRetry(4)
                .smsTemplateId(new ObjectId("60f1e6b1f075a361a94d374f"))
                .smsRetry(4)
                .priority(NotificationPriority.NOTIFICATION_PRIORITY_HIGH)
                .build();
        openCDXEmailTemplateRepository.save(openCDXEmailTemplateModel);
        openCDXSMSTemplateRespository.save(openCDXSMSTemplateModel);
        openCDXNotificationEventRepository.save(openCDXNotificationEventModel);
    }
}
