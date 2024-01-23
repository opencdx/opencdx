/**
 * The OpenCDX-Audit service performs the following key functions:
 * <ul>
 *   <li>It notes the creation, alteration, access, and removal of PII/PHI records.</li>
 *   <li>It logs modifications to a user's access controls.</li>
 *   <li>It tracks alterations to the configuration of OpenCDX.</li>
 * </ul>
 *
 * <p>
 *     These functions are performed when the client service {@link cdx.opencdx.commons.service.OpenCDXAuditService}
 *     sends audit records for logging to the OpenCDX-Audit service.
 * </p>
 *
 * @implNote The current implementation relies on a {@link cdx.opencdx.commons.service.OpenCDXMessageService}, which uses NATS.
 *
 * @implSpec To fully implement {@link cdx.opencdx.commons.service.OpenCDXMessageService}, it needs to be pushed as the primary
 * messaging service.
 *
 * @author Safe Health LLC.
 * @version 1.0
 * @since 1.0
 */
package cdx.opencdx.audit;
