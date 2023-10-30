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
package cdx.opencdx.iam.security;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.iam.service.impl.OpenCDXUserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Applicaiton Configuration
 */
@Configuration
@EnableWebSecurity
@ExcludeFromJacocoGeneratedReport
public class SecurityConfig {

    private final AuthenticationConfiguration configuration;
    private JwtTokenFilter jwtTokenFilter;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    /**
     * Default Constructor
     */
    public SecurityConfig(
            AuthenticationConfiguration configuration, OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        this.configuration = configuration;
        this.userDetailsService = new OpenCDXUserDetailsServiceImpl(openCDXIAMUserRepository);
        this.jwtTokenUtil = new JwtTokenUtil();
        this.jwtTokenFilter = new JwtTokenFilter(jwtTokenUtil, userDetailsService);
    }

    @Bean
    @Primary
    @SuppressWarnings("java:S1874")
    public UserDetailsService userDetailsService(OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        return this.userDetailsService;
    }

    @Primary
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return this.jwtTokenUtil;
    }

    @Primary
    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return this.jwtTokenFilter;
    }

    @Bean
    @Primary
    @Description("Password Encoder using the recommend Spring Delegating encoder.")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/**")
                        .permitAll()
                        .requestMatchers("/user/signup")
                        .permitAll()
                        .requestMatchers("/user/login")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, ex) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())))
                .addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

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

    // Expose authentication manager bean
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return this.configuration.getAuthenticationManager();
    }
}
