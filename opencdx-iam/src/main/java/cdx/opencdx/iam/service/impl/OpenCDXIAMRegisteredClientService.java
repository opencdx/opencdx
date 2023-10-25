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
package cdx.opencdx.iam.service.impl;

import health.safe.api.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import health.safe.api.opencdx.commons.exceptions.OpenCDXUnimplemented;
import health.safe.api.opencdx.commons.model.OpenCDXIAMUserModel;
import health.safe.api.opencdx.commons.repository.OpenCDXIAMUserRepository;
import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Slf4j
@ExcludeFromJacocoGeneratedReport
public class OpenCDXIAMRegisteredClientService implements RegisteredClientRepository {

    private final OpenCDXIAMUserRepository openCDXIAMUserRepository;

    /**
     * Constructor to create user based registered clients.
     * @param openCDXIAMUserRepository
     */
    public OpenCDXIAMRegisteredClientService(OpenCDXIAMUserRepository openCDXIAMUserRepository) {
        this.openCDXIAMUserRepository = openCDXIAMUserRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        throw new OpenCDXUnimplemented(
                "OpenCDXIAMRegisteredClientService",
                1,
                "Save Registered Client not implemented: " + registeredClient.toString());
    }

    @Override
    public RegisteredClient findById(String id) {
        RegisteredClient client = null;
        Optional<OpenCDXIAMUserModel> userModel = this.openCDXIAMUserRepository.findById(new ObjectId(id));
        if (userModel.isPresent()) {
            client = RegisteredClient.withId(id)
                    .clientId(userModel.get().getEmail())
                    .clientSecret(userModel.get().getPassword())
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenTimeToLive(Duration.ofHours(1))
                            .refreshTokenTimeToLive(Duration.ofHours(4))
                            .build())
                    .scope("USER")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .clientSettings(ClientSettings.builder()
                            .requireAuthorizationConsent(false)
                            .build())
                    .build();
        }
        return client;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        RegisteredClient client = null;
        Optional<OpenCDXIAMUserModel> userModel = this.openCDXIAMUserRepository.findByEmail(clientId);
        if (userModel.isPresent()) {
            client = RegisteredClient.withId(userModel.get().getId().toHexString())
                    .clientId(userModel.get().getEmail())
                    .clientSecret(userModel.get().getPassword())
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenTimeToLive(Duration.ofHours(1))
                            .refreshTokenTimeToLive(Duration.ofHours(4))
                            .build())
                    .scope("USER")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .clientSettings(ClientSettings.builder()
                            .requireAuthorizationConsent(false)
                            .build())
                    .build();
        }
        return client;
    }
}
