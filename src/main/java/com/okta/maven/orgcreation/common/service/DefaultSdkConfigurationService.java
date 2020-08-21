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

import com.okta.sdk.impl.client.DefaultClientBuilder;
import com.okta.sdk.impl.config.ClientConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;

public class DefaultSdkConfigurationService implements SdkConfigurationService {

    @Override
    public ClientConfiguration loadUnvalidatedConfiguration() throws ClientConfigurationException {
        try {
            Field field = DefaultClientBuilder.class.getDeclaredField("clientConfig");

            AccessController.doPrivileged((PrivilegedAction) () -> {
                field.setAccessible(true);
                return null;
            });

            return (ClientConfiguration) field.get(clientBuilder());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ClientConfigurationException("Could not load Okta SDK configuration, ensure okta-sdk-api version has " +
                    "not been changed in this plugin's configuration: " + e.getMessage(), e);
        }
    }

    @Override
    public void writeOktaYaml(String orgUrl, String apiToken, File oktaPropsFile) throws IOException {

        Map<String, Object> rootProps = new HashMap<>();
        Map<String, Object> rootOktaProps = new HashMap<>();
        Map<String, Object> clientOktaProps = new HashMap<>();

        rootProps.put("okta", rootOktaProps);
        rootOktaProps.put("client", clientOktaProps);
        clientOktaProps.put("orgUrl", orgUrl);
        clientOktaProps.put("token", apiToken);

        File parentDir = oktaPropsFile.getParentFile();

        // create parent dir
        if (!(parentDir.exists() || parentDir.mkdirs())) {
            throw new IOException("Unable to create directory: "+ parentDir.getAbsolutePath());
        }

        Yaml yaml = new Yaml();
        try (Writer writer = fileWriter(oktaPropsFile)){
            yaml.dump(rootProps, writer);
        }
    }

    DefaultClientBuilder clientBuilder() {
        return new DefaultClientBuilder();
    }

    private static Writer fileWriter(File file) throws FileNotFoundException {
        return new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
    }
}
