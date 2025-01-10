/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.chromium.androidx_crwebview.webkit.internal;

/**
 * Class containing all the startup features the AndroidX library can support.
 * The constants defined in this file should not be modified as their value is used in the
 * WebView manifest.
 */
public class StartupFeatures {
    private StartupFeatures() {
        // This class shouldn't be instantiated.
    }

    // ProcessGlobalConfig#setDataDirectorySuffix(String)
    public static final String STARTUP_FEATURE_SET_DATA_DIRECTORY_SUFFIX =
            "STARTUP_FEATURE_SET_DATA_DIRECTORY_SUFFIX";

    // ProcessGlobalConfig#setDirectoryBasePath(String)
    public static final String STARTUP_FEATURE_SET_DIRECTORY_BASE_PATH =
            "STARTUP_FEATURE_SET_DIRECTORY_BASE_PATH";

    // ProcessGlobalConfig#setPartitionedCookiesEnabled(boolean)
    public static final String STARTUP_FEATURE_CONFIGURE_PARTITIONED_COOKIES =
            "STARTUP_FEATURE_CONFIGURE_PARTITIONED_COOKIES";
}
