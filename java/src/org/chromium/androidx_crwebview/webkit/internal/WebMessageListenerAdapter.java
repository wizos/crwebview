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

import android.net.Uri;
import org.chromium.android_crwebview.webkit.WebView;

import org.chromium.androidx_crwebview.webkit.JavaScriptReplyProxy;
import org.chromium.androidx_crwebview.webkit.WebMessageCompat;
import org.chromium.androidx_crwebview.webkit.WebViewCompat;

import org.chromium.support_lib_boundary.WebMessageBoundaryInterface;
import org.chromium.support_lib_boundary.WebMessageListenerBoundaryInterface;
import org.chromium.support_lib_boundary.util.BoundaryInterfaceReflectionUtil;
import org.chromium.support_lib_boundary.util.Features;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.InvocationHandler;

/**
 * Adapter between WebViewCompat.VisualStateCallback and VisualStateCallbackBoundaryInterface (the
 * corresponding interface shared with the support library glue in the WebView APK).
 */
public class WebMessageListenerAdapter implements WebMessageListenerBoundaryInterface {
    private final WebViewCompat.WebMessageListener mWebMessageListener;

    public WebMessageListenerAdapter(WebViewCompat.@NonNull WebMessageListener webMessageListener) {
        mWebMessageListener = webMessageListener;
    }

    @Override
    public void onPostMessage(@NonNull WebView view,
            /* WebMessage */ @NonNull InvocationHandler message, @NonNull Uri sourceOrigin,
            boolean isMainFrame, /* JavaScriptReplyProxy */ @NonNull InvocationHandler replyProxy) {
        final WebMessageCompat webMessage = WebMessageAdapter.webMessageCompatFromBoundaryInterface(
                BoundaryInterfaceReflectionUtil.castToSuppLibClass(
                        WebMessageBoundaryInterface.class, message));
        if (webMessage != null) {
            JavaScriptReplyProxy jsReplyProxy =
                    JavaScriptReplyProxyImpl.forInvocationHandler(replyProxy);
            mWebMessageListener.onPostMessage(
                    view, webMessage, sourceOrigin, isMainFrame, jsReplyProxy);
        }
    }

    /**
     * This method declares which APIs the support library side supports - so that the Chromium-side
     * doesn't crash if we remove APIs from the support library side.
     */
    @Override
    public String @NonNull [] getSupportedFeatures() {
        return new String[] {Features.WEB_MESSAGE_LISTENER, Features.WEB_MESSAGE_ARRAY_BUFFER};
    }
}
