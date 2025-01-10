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

package org.chromium.androidx_crwebview.webkit;

import org.chromium.android_crwebview.webkit.CookieManager;

import androidx.annotation.AnyThread;
import androidx.annotation.RequiresFeature;
import org.chromium.androidx_crwebview.webkit.internal.ApiFeature;
import org.chromium.androidx_crwebview.webkit.internal.CookieManagerAdapter;
import org.chromium.androidx_crwebview.webkit.internal.WebViewFeatureInternal;
import org.chromium.androidx_crwebview.webkit.internal.WebViewGlueCommunicator;

import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * Compatibility version of {@link android.webkit.CookieManager}
 */
@AnyThread
public class CookieManagerCompat {
    private CookieManagerCompat() {}

    /**
     * Gets all the cookies for the given URL along with their set attributes.
     * The cookies are returned in the format of the HTTP 'Set-Cookie' header as defined in
     * <a href="https://httpwg.org/specs/rfc6265.html#sane-set-cookie-syntax">the RFC6265 spec.</a>
     *  eg. "name=value; domain=.example.com; path=/"
     *
     * @param cookieManager The CookieManager instance to get info from.
     * @param url the URL for which the API retrieves all available cookies.
     * @return the cookies as a list of strings.
     */
    @RequiresFeature(name = WebViewFeature.GET_COOKIE_INFO,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public static @NonNull List<String> getCookieInfo(@NonNull CookieManager cookieManager,
            @NonNull String url) {
        ApiFeature.NoFramework feature = WebViewFeatureInternal.GET_COOKIE_INFO;
        if (feature.isSupportedByWebView()) {
            return getAdapter(cookieManager).getCookieInfo(url);
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    private static CookieManagerAdapter getAdapter(CookieManager cookieManager) {
        return WebViewGlueCommunicator.getCompatConverter().convertCookieManager(cookieManager);
    }
}
