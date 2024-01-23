/**
 * OpenCDX Communication Microservice plays a pivotal role in enabling communication via email and SMS. It leverages a notification events system whereby events, each potentially having SMS and/or email messages affixed, are created and stored to be broadcasted upon event reception. Notification events may comprise several priority levels. Furthermore, a cron-based task runs periodically to deal with any unresolved notifications.
 *
 * This service also expertly manages the communication with the CDC.
 *
 * Fired by incoming messages from {@link cdx.opencdx.commons.service.OpenCDXMessageService}, it triggers both the Notification events and CDC messages.
 *
 * @author Safe Health LLC.
 * @version 1.0
 * @since 1.0
 */
package cdx.opencdx.communications;
