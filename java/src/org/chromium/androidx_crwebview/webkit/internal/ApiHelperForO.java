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

import android.content.pm.PackageInfo;
import android.os.Build;
import org.chromium.android_crwebview.webkit.WebChromeClient;
import org.chromium.android_crwebview.webkit.WebSettings;
import org.chromium.android_crwebview.webkit.WebView;
import org.chromium.android_crwebview.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Utility class to use new APIs that were added in O (API level 26).
 * These need to exist in a separate class so that Android framework can successfully verify
 * classes without encountering the new APIs.
 */
@RequiresApi(Build.VERSION_CODES.O)
public class ApiHelperForO {
    private ApiHelperForO() {}


    /**
     * @see WebSettings#setSafeBrowsingEnabled(boolean)
     */
    public static void setSafeBrowsingEnabled(@NonNull WebSettings webSettings, boolean b) {
        webSettings.setSafeBrowsingEnabled(b);
    }

    /**
     * @see WebSettings#getSafeBrowsingEnabled()
     */
    public static boolean getSafeBrowsingEnabled(@NonNull WebSettings webSettings) {
        return webSettings.getSafeBrowsingEnabled();
    }

    /**
     * @see WebView#getWebViewClient()
     */
    public static @Nullable WebViewClient getWebViewClient(@NonNull WebView webView) {
        return webView.getWebViewClient();
    }

    /**
     * @see WebView#getWebChromeClient()
     */
    public static @Nullable WebChromeClient getWebChromeClient(@NonNull WebView webView) {
        return webView.getWebChromeClient();
    }

    /**
     * @see WebView#getCurrentWebViewPackage()
     */
    public static @NonNull PackageInfo getCurrentWebViewPackage() {
        return WebView.getCurrentWebViewPackage();
    }
}
