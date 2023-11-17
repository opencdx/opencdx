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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.exceptions.OpenCDXUnauthorized;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.audit.AgentType;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Implementaiton based on JWT
 */
@ExcludeFromJacocoGeneratedReport
@Service
public class OpenCDXCurrentUserImpl implements OpenCDXCurrentUser {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String DOMAIN = "OpenCDXCurrentUserImpl";
    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    /**
     * Consturtor for OpenCDXCurrentUser service.
     * @param openCDXIAMUserRepository Repository to access users.
     */
    public OpenCDXCurrentUserImpl(
            OpenCDXIAMUserRepository openCDXIAMUserRepository,
            @Value("${spring.application.name}") String applicationName) {
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
        this.applicationName = applicationName;
    }

    @Override
    public OpenCDXIAMUserModel getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return this.openCDXIAMUserRepository
                    .findByUsername(authentication.getName())
                    .orElseThrow(() ->
                            new OpenCDXNotFound(DOMAIN, 2, "Current User not found: " + authentication.getName()));
        }
        throw new OpenCDXUnauthorized(DOMAIN, 1, "No user Authenticated. No Current User.");
    }

    @Override
    public OpenCDXIAMUserModel getCurrentUser(OpenCDXIAMUserModel defaultUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return this.openCDXIAMUserRepository
                    .findByUsername(authentication.getName())
                    .orElseThrow(() ->
                            new OpenCDXNotFound(DOMAIN, 2, "Current User not found: " + authentication.getName()));
        }
        return defaultUser;
    }

    @Override
    public AgentType getCurrentUserType() {
        return AgentType.AGENT_TYPE_HUMAN_USER;
    }

    @Override
    public void configureAuthentication(String role) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                this.applicationName, role, List.of(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
