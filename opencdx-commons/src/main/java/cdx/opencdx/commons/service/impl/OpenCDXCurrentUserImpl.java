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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.exceptions.OpenCDXUnauthorized;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.iam.IamUserType;
import io.micrometer.observation.annotation.Observed;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
@Slf4j
@ExcludeFromJacocoGeneratedReport
@Service
@Observed(name = "opencdx")
public class OpenCDXCurrentUserImpl implements OpenCDXCurrentUser {

    private static final String CURRENT_USER_NOT_FOUND = "Current User not found: ";
    private static final String BYPASSING_AUTHENTICATION = "Bypassing Authentication";

    @Value("${spring.application.name}")
    private String applicationName;

    private boolean allowBypassAuthentication = false;

    private static final String DOMAIN = "OpenCDXCurrentUserImpl";
    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    /**
     * Consturtor for OpenCDXCurrentUser service.
     * @param openCDXIAMUserRepository Repository to access users.
     * @param applicationName Name of application used as user
     */
    public OpenCDXCurrentUserImpl(
            OpenCDXIAMUserRepository openCDXIAMUserRepository,
            @Value("${spring.application.name}") String applicationName) {
        log.info("OpenCDXCurrentUserImpl created");
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
        this.applicationName = applicationName;
    }

    @Override
    public OpenCDXIAMUserModel getCurrentUser() {
        log.info("Getting current user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null && this.allowBypassAuthentication) {
            log.warn(BYPASSING_AUTHENTICATION);
            return OpenCDXIAMUserModel.builder()
                    .systemName("ByPass User")
                    .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                    .id(new ObjectId("000000000000000000000001"))
                    .build();
        }
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return this.openCDXIAMUserRepository
                    .findByUsername(authentication.getName())
                    .orElseThrow(
                            () -> new OpenCDXNotFound(DOMAIN, 2, CURRENT_USER_NOT_FOUND + authentication.getName()));
        }
        throw new OpenCDXUnauthorized(DOMAIN, 1, "No user Authenticated. No Current User.");
    }

    public OpenCDXIAMUserModel checkCurrentUser(OpenCDXIAMUserModel defaultUser) {
        log.info("Checking current user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && defaultUser.getUsername().equals(authentication.getName())) {
            return defaultUser;
        } else {
            return getCurrentUser();
        }
    }

    @Override
    public OpenCDXIAMUserModel getCurrentUser(OpenCDXIAMUserModel defaultUser) {
        log.info("Getting current user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return this.openCDXIAMUserRepository
                    .findByUsername(authentication.getName())
                    .orElseThrow(
                            () -> new OpenCDXNotFound(DOMAIN, 2, CURRENT_USER_NOT_FOUND + authentication.getName()));
        }
        return defaultUser;
    }

    @Override
    public void configureAuthentication(String role) {
        log.info("Configuring Authentication");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                this.applicationName, role, List.of(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void allowBypassAuthentication(boolean allowBypassAuthentication) {
        if (allowBypassAuthentication) {
            log.warn(BYPASSING_AUTHENTICATION);
        } else {
            log.warn("Requiring Authentication");
        }
        this.allowBypassAuthentication = allowBypassAuthentication;
    }

    @Override
    public String getCurrentUserAccessToken() {
        log.info("Getting current user access token");
        return new JwtTokenUtil().generateAccessToken(getCurrentUser());
    }
}
