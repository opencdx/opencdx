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
package cdx.opencdx.commons.security;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.security.AuthenticationSchemeSelector;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Schema for JWT Token in gRPC
 */
@Slf4j
@ExcludeFromJacocoGeneratedReport
public class JwtTokenGrpcSchema implements AuthenticationSchemeSelector {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor for JWT Token Schema for gRPC
     * @param jwtTokenUtil Utility class for JWT Tokens
     * @param userDetailsService UserDetailsService for accessing user records.
     */
    public JwtTokenGrpcSchema(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        log.trace("Created JwtTokenGrpcSchema");
    }

    @Override
    public Optional<Authentication> getAuthScheme(CharSequence authorization) {
        String header = authorization.toString();
        if (header.startsWith("Bearer ")) {
            // Get jwt token and validate
            final String token = header.split(" ")[1].trim();
            if (jwtTokenUtil.validate(token)) {
                // Get user identity and set it on the spring security context
                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsername(token));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails == null ? List.of() : userDetails.getAuthorities());

                return Optional.of(authentication);
            } else {
                log.error("Token failed validation");
            }
        } else {
            log.error("Failed to find bearer token");
        }
        return Optional.empty();
    }
}
