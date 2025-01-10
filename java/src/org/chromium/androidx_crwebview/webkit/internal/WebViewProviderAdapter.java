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

package org.chromium.androidx_crwebview.webkit.internal;

import android.annotation.SuppressLint;
import android.net.Uri;
import org.chromium.android_crwebview.webkit.ValueCallback;
import org.chromium.android_crwebview.webkit.WebChromeClient;
import org.chromium.android_crwebview.webkit.WebView;
import org.chromium.android_crwebview.webkit.WebViewClient;

import androidx.core.os.CancellationSignal;
import org.chromium.androidx_crwebview.webkit.PrerenderException;
import org.chromium.androidx_crwebview.webkit.PrerenderOperationCallback;
import org.chromium.androidx_crwebview.webkit.Profile;
import org.chromium.androidx_crwebview.webkit.SpeculativeLoadingParameters;
import org.chromium.androidx_crwebview.webkit.WebMessageCompat;
import org.chromium.androidx_crwebview.webkit.WebMessagePortCompat;
import org.chromium.androidx_crwebview.webkit.WebViewCompat;
import org.chromium.androidx_crwebview.webkit.WebViewRenderProcess;
import org.chromium.androidx_crwebview.webkit.WebViewRenderProcessClient;

import org.chromium.support_lib_boundary.ProfileBoundaryInterface;
import org.chromium.support_lib_boundary.WebViewProviderBoundaryInterface;
import org.chromium.support_lib_boundary.util.BoundaryInterfaceReflectionUtil;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationHandler;
import java.util.concurrent.Executor;

/**
 * Adapter for WebViewProviderBoundaryInterface providing the functionality expected of
 * WebViewCompat, this adapter is the support library version of
 * {@link android.webkit.WebViewProvider}.
 */
@SuppressWarnings("JavadocReference") // WebViewProvider is hidden.
public class WebViewProviderAdapter {
    final WebViewProviderBoundaryInterface mImpl;

    public WebViewProviderAdapter(@NonNull WebViewProviderBoundaryInterface impl) {
        mImpl = impl;
    }

    /**
     * Adapter method WebViewCompat.insertVisualStateCallback().
     */
    public void insertVisualStateCallback(
            long requestId, WebViewCompat.@NonNull VisualStateCallback callback) {
        mImpl.insertVisualStateCallback(requestId,
                BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(
                        new VisualStateCallbackAdapter(callback)));
    }

    /**
     * Adapter method for {@link WebViewCompat#createWebMessageChannel(WebView)}.
     */
    public WebMessagePortCompat @NonNull [] createWebMessageChannel() {
        InvocationHandler[] invocationHandlers = mImpl.createWebMessageChannel();
        WebMessagePortCompat[] messagePorts = new WebMessagePortCompat[invocationHandlers.length];
        for (int n = 0; n < invocationHandlers.length; n++) {
            messagePorts[n] = new WebMessagePortImpl(invocationHandlers[n]);
        }
        return messagePorts;
    }

    /**
     * Adapter method for {@link WebViewCompat#postWebMessage(WebView, WebMessageCompat, Uri)}.
     */
    public void postWebMessage(@NonNull WebMessageCompat message, @NonNull Uri targetOrigin) {
        mImpl.postMessageToMainFrame(
                BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(
                        new WebMessageAdapter(message)), targetOrigin);
    }

    /**
     * Adapter method for {@link WebViewCompat#addWebMessageListener(android.webkit.WebView,
     * String, java.util.List, androidx.webkit.WebViewCompat.WebMessageListener)}.
     */
    public void addWebMessageListener(@NonNull String jsObjectName,
            String @NonNull [] allowedOriginRules,
            WebViewCompat.@NonNull WebMessageListener listener) {
        mImpl.addWebMessageListener(jsObjectName, allowedOriginRules,
                BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(
                        new WebMessageListenerAdapter(listener)));
    }

    /**
     * Adapter method for {@link WebViewCompat#addWebMessageListener(android.webkit.WebView,
     * String, Set)}
     */
    public @NonNull ScriptHandlerImpl addDocumentStartJavaScript(
            @NonNull String script, String @NonNull [] allowedOriginRules) {
        return ScriptHandlerImpl.toScriptHandler(
                mImpl.addDocumentStartJavaScript(script, allowedOriginRules));
    }

    /**
     * Adapter method for {@link WebViewCompat#removeWebMessageListener(String)}.
     */
    public void removeWebMessageListener(@NonNull String jsObjectName) {
        mImpl.removeWebMessageListener(jsObjectName);
    }

    /**
     * Adapter method for {@link WebViewCompat#getWebViewClient()}.
     */
    public @NonNull WebViewClient getWebViewClient() {
        return mImpl.getWebViewClient();
    }

    /**
     * Adapter method for {@link WebViewCompat#getWebChromeClient()}.
     */
    public @Nullable WebChromeClient getWebChromeClient() {
        return mImpl.getWebChromeClient();
    }

    /**
     * Adapter method for {@link WebViewCompat#getWebViewRenderer()}.
     */
    public @Nullable WebViewRenderProcess getWebViewRenderProcess() {
        return WebViewRenderProcessImpl.forInvocationHandler(mImpl.getWebViewRenderer());
    }

    /**
     * Adapter method for {@link WebViewCompat#getWebViewRendererClient()}.
     */
    public @Nullable WebViewRenderProcessClient getWebViewRenderProcessClient() {
        InvocationHandler handler = mImpl.getWebViewRendererClient();
        if (handler == null) return null;
        return ((WebViewRenderProcessClientAdapter)
                BoundaryInterfaceReflectionUtil.getDelegateFromInvocationHandler(
                        handler)).getWebViewRenderProcessClient();
    }

    /**
     * Adapter method for {@link WebViewCompat#setWebViewRendererClient(WebViewRendererClient)}.
     */
    // WebViewRenderProcessClient is a callback class, so it should be last. See
    // https://issuetracker.google.com/issues/139770271.
    @SuppressLint("LambdaLast")
    public void setWebViewRenderProcessClient(@Nullable Executor executor,
            @Nullable WebViewRenderProcessClient webViewRenderProcessClient) {
        InvocationHandler handler = webViewRenderProcessClient != null
                ? BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(
                        new WebViewRenderProcessClientAdapter(executor, webViewRenderProcessClient))
                : null;
        mImpl.setWebViewRendererClient(handler);
    }

    /**
     * Adapter method for {@link WebViewCompat#setProfile(WebView, String)}.
     */
    public void setProfileWithName(@NonNull String profileName) {
        mImpl.setProfile(profileName);
    }

    /**
     * Adapter method for {@link WebViewCompat#getProfile(WebView)}.
     */
    public @NonNull Profile getProfile() {
        ProfileBoundaryInterface profile = BoundaryInterfaceReflectionUtil.castToSuppLibClass(
                ProfileBoundaryInterface.class, mImpl.getProfile());

        return new ProfileImpl(profile);
    }

    /**
     * Adapter method for {@link WebViewCompat#isAudioMuted(WebView)}.
     */
    public boolean isAudioMuted() {
        return mImpl.isAudioMuted();
    }

    /**
     * Adapter method for {@link WebViewCompat#setAudioMuted(WebView, boolean)}.
     */
    public void setAudioMuted(boolean mute) {
        mImpl.setAudioMuted(mute);
    }

    /**
     * Adapter method for
     * {@link WebViewCompat#prerenderUrlAsync(WebView, String, CancellationSignal,
     * PrerenderOperationCallback)}.
     */
    public void prerenderUrlAsync(
            @NonNull String url,
            @Nullable CancellationSignal cancellationSignal,
            @NonNull PrerenderOperationCallback callback) {

        ValueCallback<Void> activationCallback = (value) -> {
            // value will always be null.
            callback.onPrerenderActivated();
        };
        ValueCallback<Throwable> errorCallback = (throwable) -> {
            callback.onError(new PrerenderException("Prerender operation failed", throwable));
        };
        mImpl.prerenderUrl(
                url,
                cancellationSignal,
                activationCallback,
                errorCallback);
    }

    /**
     * Adapter method for
     * {@link WebViewCompat#prerenderUrlAsync(WebView, String, CancellationSignal,
     * SpeculativeLoadingParameters, PrerenderOperationCallback)}.
     */
    public void prerenderUrlAsync(
            @NonNull String url,
            @Nullable CancellationSignal cancellationSignal,
            @NonNull SpeculativeLoadingParameters params,
            @NonNull PrerenderOperationCallback callback) {

        InvocationHandler paramsBoundaryInterface =
                BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(
                        new SpeculativeLoadingParametersAdapter(params));
        ValueCallback<Void> activationCallback = (value) -> {
            // value will always be null.
            callback.onPrerenderActivated();
        };
        ValueCallback<Throwable> errorCallback = (throwable) -> {
            callback.onError(new PrerenderException("Prerender operation failed", throwable));
        };
        mImpl.prerenderUrl(
                url,
                cancellationSignal,
                paramsBoundaryInterface,
                activationCallback,
                errorCallback);
    }
}
