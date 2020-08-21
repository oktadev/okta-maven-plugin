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

public interface ProgressBar extends AutoCloseable {

    ProgressBar start();

    ProgressBar start(CharSequence message);

    void info(CharSequence message);

    @Override
    void close();

    static ProgressBar create(boolean interactive) {

        if (interactive) {
            return new ConsoleProgressBar();
        } else {
            return new LoggerProgressBar();
        }
    }
}
