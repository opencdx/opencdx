/**
 * The OpenCDX Gateway, built on the Spring Gateway, primarily routes REST and gRPC requests to the appropriate microservice for further processing.
 *
 * @implSpec Gateway's gRPC routes require manual registration via modifications in the application.yml file.
 * @implNote One distinctive feature of this class is that it prevents external calls to actuator endpoints.
 *
 * @author Safe Health LLC
 * @version 1.0
 * @since 1.0
 */
package cdx.opencdx.gateway;
