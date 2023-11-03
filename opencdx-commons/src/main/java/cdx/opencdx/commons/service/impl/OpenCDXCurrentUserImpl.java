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

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import java.util.Optional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OpenCDXCurrentUserImpl implements OpenCDXCurrentUser {

    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    public OpenCDXCurrentUserImpl(OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
    }

    @Override
    public Optional<OpenCDXIAMUserModel> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return this.openCDXIAMUserRepository.findByEmail(authentication.getName());
        }
        return Optional.empty();
    }
}
