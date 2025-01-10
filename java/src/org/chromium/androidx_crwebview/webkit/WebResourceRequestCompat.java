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

import org.chromium.android_crwebview.webkit.WebResourceRequest;

import androidx.annotation.RequiresFeature;
import org.chromium.androidx_crwebview.webkit.internal.ApiFeature;
import org.chromium.androidx_crwebview.webkit.internal.ApiHelperForN;
import org.chromium.androidx_crwebview.webkit.internal.WebResourceRequestAdapter;
import org.chromium.androidx_crwebview.webkit.internal.WebViewFeatureInternal;
import org.chromium.androidx_crwebview.webkit.internal.WebViewGlueCommunicator;

import org.jspecify.annotations.NonNull;

/**
 * Compatibility version of {@link WebResourceRequest}.
 */
public class WebResourceRequestCompat {

    // Developers should not be able to instantiate this class.
    private WebResourceRequestCompat() {}

    /**
     * Gets whether the request was a result of a server-side redirect.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#WEB_RESOURCE_REQUEST_IS_REDIRECT}.
     *
     * @return whether the request was a result of a server-side redirect.
     */
    @RequiresFeature(name = WebViewFeature.WEB_RESOURCE_REQUEST_IS_REDIRECT,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public static boolean isRedirect(@NonNull WebResourceRequest request) {
        ApiFeature.N feature = WebViewFeatureInternal.WEB_RESOURCE_REQUEST_IS_REDIRECT;
        if (feature.isSupportedByFramework()) {
            return ApiHelperForN.isRedirect(request);
        } else if (feature.isSupportedByWebView()) {
            return getAdapter(request).isRedirect();
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    private static WebResourceRequestAdapter getAdapter(WebResourceRequest request) {
        return WebViewGlueCommunicator.getCompatConverter().convertWebResourceRequest(request);
    }
}
