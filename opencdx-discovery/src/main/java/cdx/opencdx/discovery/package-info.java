/**
 * The OpenCDX Discovery server, which is based on the Spring Discovery Server using Eureka, is designed to manage instances of microservices. Services are channeled to this server via configuration.
 * @implNote Please note that at this time, gRPC Services do not register with the discovery server, and only REST APIs are detected by the OpenCDX Gateway for routing. gRPC routes are not currently supported.
 * @author Safe Health LLC.
 * @version 1.0
 * @since 1.0
 */
package cdx.opencdx.discovery;
