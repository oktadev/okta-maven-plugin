/*
 * Copyright 2020-Present Okta, Inc.
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
package com.okta.maven.orgcreation

import com.okta.maven.orgcreation.common.config.MutablePropertySource
import com.okta.maven.orgcreation.common.service.SdkConfigurationService
import com.okta.maven.orgcreation.common.service.SetupService
import com.okta.sdk.impl.config.ClientConfiguration
import com.okta.sdk.resource.application.OpenIdConnectApplicationType
import org.testng.annotations.Test

import static org.mockito.Mockito.*

class JHipsterMojoTest {

    @Test
    void defaultValuesTest() {

        File projectDir = File.createTempDir()
        SetupService setupService = mock(SetupService)
        MutablePropertySource propertySource = mock(MutablePropertySource)
        SdkConfigurationService sdkConfigurationService = mock(SdkConfigurationService)
        ClientConfiguration clientConfiguration = mock(ClientConfiguration)
        when(sdkConfigurationService.loadUnvalidatedConfiguration()).thenReturn(clientConfiguration)
        when(clientConfiguration.getBaseUrl()).thenReturn("https://test.example.com")
        when(clientConfiguration.getApiToken()).thenReturn("test-api-token")

        JHipsterMojo mojo = spy(new JHipsterMojo(){
            @Override
            SetupService createSetupService(String springPropertyKey) {
                return setupService
            }

            @Override
            MutablePropertySource getPropertySource() {
                return propertySource
            }
        })
        mojo.oidcAppName = "test-app-name"
        mojo.baseDir = projectDir
        mojo.sdkConfigurationService = sdkConfigurationService

        mojo.execute()

        verify(mojo).createSetupService("oidc")
        verify(setupService).createOidcApplication(propertySource, "test-app-name", "https://test.example.com", "groups", null, "default", true, OpenIdConnectApplicationType.WEB, "http://localhost:8080/login/oauth2/code/oidc")
    }
}
