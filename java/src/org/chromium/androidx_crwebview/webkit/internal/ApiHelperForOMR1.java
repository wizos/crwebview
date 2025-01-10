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

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import org.chromium.android_crwebview.webkit.SafeBrowsingResponse;
import org.chromium.android_crwebview.webkit.ValueCallback;
import org.chromium.android_crwebview.webkit.WebView;

import androidx.annotation.RequiresApi;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Utility class to use new APIs that were added in O_MR1 (API level 27).
 * These need to exist in a separate class so that Android framework can successfully verify
 * classes without encountering the new APIs.
 */
@RequiresApi(Build.VERSION_CODES.O_MR1)
public class ApiHelperForOMR1 {
    private ApiHelperForOMR1() {
    }


    /**
     * @see SafeBrowsingResponse#showInterstitial(boolean)
     */
    public static void showInterstitial(@NonNull SafeBrowsingResponse safeBrowsingResponse,
            boolean showInterstitial) {
        safeBrowsingResponse.showInterstitial(showInterstitial);
    }

    /**
     * @see SafeBrowsingResponse#proceed(boolean)
     */
    public static void proceed(@NonNull SafeBrowsingResponse frameworksImpl, boolean proceed) {
        frameworksImpl.proceed(proceed);
    }

    /**
     * @see SafeBrowsingResponse#backToSafety(boolean)
     */
    public static void backToSafety(@NonNull SafeBrowsingResponse safeBrowsingResponse,
            boolean backToSafety) {
        safeBrowsingResponse.backToSafety(backToSafety);
    }

    /**
     * @see WebView#startSafeBrowsing(Context, ValueCallback)
     */
    public static void startSafeBrowsing(@NonNull Context context,
            @Nullable ValueCallback<Boolean> callback) {
        WebView.startSafeBrowsing(context, callback);
    }

    /**
     * @see WebView#setSafeBrowsingWhitelist(List, ValueCallback)
     */
    public static void setSafeBrowsingWhitelist(@NonNull List<String> hosts,
            @Nullable ValueCallback<Boolean> callback) {
        WebView.setSafeBrowsingWhitelist(hosts, callback);
    }

    /**
     * @see WebView#getSafeBrowsingPrivacyPolicyUrl()
     */
    public static @NonNull Uri getSafeBrowsingPrivacyPolicyUrl() {
        return WebView.getSafeBrowsingPrivacyPolicyUrl();
    }
}
