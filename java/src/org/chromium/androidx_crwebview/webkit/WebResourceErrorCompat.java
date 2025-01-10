/*
 * Copyright 2018 The Android Open Source Project
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

package org.chromium.androidx_crwebview.webkit;

import org.chromium.android_crwebview.webkit.WebResourceError;
import org.chromium.android_crwebview.webkit.WebViewClient;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresFeature;
import androidx.annotation.RestrictTo;

import org.jspecify.annotations.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Compatibility version of {@link WebResourceError}.
 */
public abstract class WebResourceErrorCompat {
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @IntDef(value = {
            WebViewClient.ERROR_UNKNOWN,
            WebViewClient.ERROR_HOST_LOOKUP,
            WebViewClient.ERROR_UNSUPPORTED_AUTH_SCHEME,
            WebViewClient.ERROR_AUTHENTICATION,
            WebViewClient.ERROR_PROXY_AUTHENTICATION,
            WebViewClient.ERROR_CONNECT,
            WebViewClient.ERROR_IO,
            WebViewClient.ERROR_TIMEOUT,
            WebViewClient.ERROR_REDIRECT_LOOP,
            WebViewClient.ERROR_UNSUPPORTED_SCHEME,
            WebViewClient.ERROR_FAILED_SSL_HANDSHAKE,
            WebViewClient.ERROR_BAD_URL,
            WebViewClient.ERROR_FILE,
            WebViewClient.ERROR_FILE_NOT_FOUND,
            WebViewClient.ERROR_TOO_MANY_REQUESTS,
            WebViewClient.ERROR_UNSAFE_RESOURCE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface NetErrorCode {}

    /**
     * Gets the error code of the error. The code corresponds to one
     * of the {@code ERROR_*} constants in {@link WebViewClient}.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#WEB_RESOURCE_ERROR_GET_CODE}.
     *
     * @return The error code of the error
     */
    @RequiresFeature(name = WebViewFeature.WEB_RESOURCE_ERROR_GET_CODE,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract @NetErrorCode int getErrorCode();

    /**
     * Gets the string describing the error. Descriptions are localized,
     * and thus can be used for communicating the problem to the user.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#WEB_RESOURCE_ERROR_GET_DESCRIPTION}.
     *
     * @return The description of the error
     */
    @RequiresFeature(name = WebViewFeature.WEB_RESOURCE_ERROR_GET_DESCRIPTION,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract @NonNull CharSequence getDescription();

    /**
     * This class cannot be created by applications.
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public WebResourceErrorCompat() {
    }
}
