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
package com.okta.maven.orgcreation.common.progressbar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LoggerProgressBar implements ProgressBar {

    private static Logger LOG = LoggerFactory.getLogger(LoggerProgressBar.class);

    @Override
    public ProgressBar start() {
        return this;
    }

    @Override
    public ProgressBar start(CharSequence message) {
        info(message);
        return this;
    }

    @Override
    public void info(CharSequence message) {
        if (message != null) {
            LOG.info(message.toString());
        }
    }

    @Override
    public void close() {

    }
}
