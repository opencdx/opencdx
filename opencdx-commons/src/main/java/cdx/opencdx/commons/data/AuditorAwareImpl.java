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
package cdx.opencdx.commons.data;

import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

/**
 * Implementation of AuditorAware for OpenCDX
 */
public class AuditorAwareImpl implements AuditorAware<OpenCDXIdentifier> {

    final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor for AuditorAwareImpl
     * @param openCDXCurrentUser OpenCDXCurrentUser service
     */
    public AuditorAwareImpl(OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    @Override
    public Optional<OpenCDXIdentifier> getCurrentAuditor() {
        return Optional.of(this.openCDXCurrentUser.getCurrentUser().getId());
    }
}
