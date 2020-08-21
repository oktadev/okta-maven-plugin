/*
 * Copyright 2018-Present Okta, Inc.
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
package com.okta.maven.orgcreation.common.service;

import com.okta.maven.orgcreation.common.FactorVerificationException;
import com.okta.maven.orgcreation.common.RestException;
import com.okta.maven.orgcreation.common.model.OrganizationRequest;
import com.okta.maven.orgcreation.common.model.OrganizationResponse;

import java.io.IOException;

public interface OktaOrganizationCreator {

    OrganizationResponse createNewOrg(String apiBaseUrl, OrganizationRequest orgRequest) throws IOException, RestException;

    OrganizationResponse verifyNewOrg(String apiBaseUrl, String identifier, String code) throws FactorVerificationException, IOException;
}
