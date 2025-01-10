/*
 * Copyright 2019 The Android Open Source Project
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

import org.chromium.android_crwebview.webkit.WebView;

import androidx.annotation.RequiresApi;
import org.chromium.androidx_crwebview.webkit.WebViewRenderProcessClient;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Adapter class to pass a {@link android.webkit.WebViewRenderProcessClient} over to chromium.
 */
@RequiresApi(29)
public class WebViewRenderProcessClientFrameworkAdapter extends
            android.webkit.WebViewRenderProcessClient {
    private final WebViewRenderProcessClient mWebViewRenderProcessClient;

    public WebViewRenderProcessClientFrameworkAdapter(@NonNull WebViewRenderProcessClient client) {
        mWebViewRenderProcessClient = client;
    }

    @Override
    public void onRenderProcessUnresponsive(@NonNull WebView view,
                final android.webkit.@Nullable WebViewRenderProcess renderer) {
        mWebViewRenderProcessClient.onRenderProcessUnresponsive(view,
                WebViewRenderProcessImpl.forFrameworkObject(renderer));
    }

    @Override
    public void onRenderProcessResponsive(@NonNull WebView view,
                final android.webkit.@Nullable WebViewRenderProcess renderer) {
        mWebViewRenderProcessClient.onRenderProcessResponsive(view,
                WebViewRenderProcessImpl.forFrameworkObject(renderer));
    }

    public @Nullable WebViewRenderProcessClient getFrameworkRenderProcessClient() {
        return mWebViewRenderProcessClient;
    }
}
