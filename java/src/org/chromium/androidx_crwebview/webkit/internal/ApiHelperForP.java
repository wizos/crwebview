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

import android.os.Build;
import android.os.Looper;
import org.chromium.android_crwebview.webkit.TracingController;
import org.chromium.android_crwebview.webkit.WebView;

import androidx.annotation.RequiresApi;
import org.chromium.androidx_crwebview.webkit.TracingConfig;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.OutputStream;
import java.util.concurrent.Executor;

/**
 * Utility class to use new APIs that were added in P (API level 28).
 * These need to exist in a separate class so that Android framework can successfully verify
 * classes without encountering the new APIs.
 */
@RequiresApi(Build.VERSION_CODES.P)
public class ApiHelperForP {
    private ApiHelperForP() {
    }

    /**
     * @see TracingController#getInstance()
     */
    public static @NonNull TracingController getTracingControllerInstance() {
        return TracingController.getInstance();
    }

    /**
     * @see TracingController#isTracing()
     */
    public static boolean isTracing(@NonNull TracingController tracingController) {
        return tracingController.isTracing();
    }

    /**
     * Converts the passed {@link TracingConfig} to {@link android.webkit.TracingConfig} to
     * isolate new types in this class.
     * @see TracingController#start(android.webkit.TracingConfig)
     */
    public static void start(@NonNull TracingController tracingController,
            @NonNull TracingConfig tracingConfig) {
        android.webkit.TracingConfig config =
                new android.webkit.TracingConfig.Builder()
                        .addCategories(tracingConfig.getPredefinedCategories())
                        .addCategories(tracingConfig.getCustomIncludedCategories())
                        .setTracingMode(tracingConfig.getTracingMode())
                        .build();
        tracingController.start(config);
    }

    /**
     * @see TracingController#stop(OutputStream, Executor)
     */
    public static boolean stop(@NonNull TracingController tracingController,
            @Nullable OutputStream os, @NonNull Executor ex) {
        return tracingController.stop(os, ex);
    }

    /**
     * @see WebView#getWebViewClassLoader()
     */
    public static @NonNull ClassLoader getWebViewClassLoader() {
        return WebView.getWebViewClassLoader();
    }

    /**
     * @see WebView#getWebViewLooper()
     */
    public static @NonNull Looper getWebViewLooper(@NonNull WebView webView) {
        return webView.getWebViewLooper();
    }


    /**
     * @see WebView#setDataDirectorySuffix(String)
     */
    public static void setDataDirectorySuffix(@NonNull String suffix) {
        WebView.setDataDirectorySuffix(suffix);
    }
}
