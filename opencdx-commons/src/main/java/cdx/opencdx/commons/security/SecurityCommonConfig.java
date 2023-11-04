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
package cdx.opencdx.commons.security;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.impl.OpenCDXUserDetailsServiceImpl;
import java.util.Arrays;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Application Configuration
 */
@AutoConfiguration
@Configuration
@Profile({"managed", "mongo"})
@ExcludeFromJacocoGeneratedReport
public class SecurityCommonConfig {

    private final AuthenticationConfiguration configuration;
    private JwtTokenFilter jwtTokenFilter;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Security Common Configuration
     * @param configuration Authentication Configuration
     * @param openCDXIAMUserRepository Repository for accessing users.
     */
    public SecurityCommonConfig(
            AuthenticationConfiguration configuration, OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        this.configuration = configuration;
        this.userDetailsService = new OpenCDXUserDetailsServiceImpl(openCDXIAMUserRepository);
        this.jwtTokenUtil = new JwtTokenUtil();
        this.jwtTokenFilter = new JwtTokenFilter(jwtTokenUtil, userDetailsService);
    }

    /**
     * Get the GRPC Token Schema
     * @return JwtTokenGrpcSchema to use for users
     */
    @Bean
    @Primary
    public JwtTokenGrpcSchema jwtTokenGrpcSchema() {
        return new JwtTokenGrpcSchema(this.jwtTokenUtil, this.userDetailsService);
    }

    /**
     * UserDetailsService based on the OpenCDX
     * @return the UserDetailsService to use.
     */
    @Bean
    @Primary
    @SuppressWarnings("java:S1874")
    public UserDetailsService userDetailsService() {
        return this.userDetailsService;
    }

    /**
     * Utility bean for JWt tokens
     * @return JwtTokenUtil
     */
    @Primary
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return this.jwtTokenUtil;
    }

    /**
     * The Token Filer for JWT
     * @return JwtTokenFilter for OpenCDX
     */
    @Primary
    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return this.jwtTokenFilter;
    }

    /**
     * CORS Configuration
     * @return CorsConfigurationSource to used
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
