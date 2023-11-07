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
package cdx.opencdx.commons.dto;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * OpenCDX Implementation of the User Details
 */
@Data
@AllArgsConstructor
@Builder
@ExcludeFromJacocoGeneratedReport
public class OpenCDXUserDetails implements UserDetails {
    /**
     * Default Constructor
     */
    public OpenCDXUserDetails() {
        // Explicit left empty
    }

    /**
     * Email address used as username
     */
    private String email;

    /**
     * Encrypted Password for user.
     */
    private String password;

    /**
     * Boolean indicating if user is enabled.
     */
    @Builder.Default
    private boolean enabled = true;

    /**
     * Boolean indicating if account expired.
     */
    @Builder.Default
    private boolean accountExpired = false;

    /**
     * Boolean indicating if credentials expired
     */
    @Builder.Default
    private boolean credentialsExpired = false;

    /**
     * Boolean indicating if account is locked
     */
    @Builder.Default
    private boolean accountLocked = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("OPENCDX_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
