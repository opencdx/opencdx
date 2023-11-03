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
import cdx.opencdx.commons.dto.OpenCDXUserDetails;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.grpc.iam.IamUserStatus;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * OpenCDX User Details Service for IAM
 */
@Slf4j
@ExcludeFromJacocoGeneratedReport
public class OpenCDXUserDetailsServiceImpl implements UserDetailsService {

    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    /**
     * Constructor to access the User Repository
     *
     * @param openCDXIAMUserRepository
     */
    public OpenCDXUserDetailsServiceImpl(OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
    }

    @Override
    @Cacheable("user-details")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OpenCDXIAMUserModel> model = this.openCDXIAMUserRepository.findByEmail(username);

        if (model.isPresent()) {

            return OpenCDXUserDetails.builder()
                    .email(model.get().getEmail())
                    .password(model.get().getPassword())
                    .enabled(model.get().getStatus().equals(IamUserStatus.IAM_USER_STATUS_ACTIVE))
                    .accountExpired(model.get().isAccountExpired())
                    .credentialsExpired(model.get().isCredentialsExpired())
                    .accountLocked(model.get().isAccountLocked())
                    .build();
        }

        log.error("Failed to find user: {}", username);
        throw new UsernameNotFoundException("username");
    }
}
