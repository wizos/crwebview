/*
 * Copyright (C) 2012 The Android Open Source Project
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

package org.chromium.android_crwebview.webkit;

import androidx.annotation.NonNull;
import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.net.Uri;

import java.util.List;

/**
 * This is the main entry-point into the WebView back end implementations, which the WebView
 * proxy class uses to instantiate all the other objects as needed. The backend must provide an
 * implementation of this interface, and make it available to the WebView via mechanism TBD.
 * @hide
 */
public interface WebViewFactoryProvider {
    /**
     * This Interface provides glue for implementing the backend of WebView static methods which
     * cannot be implemented in-situ in the proxy class.
     */
    interface Statics {
        /**
         * Implements the API method:
         * {@link android.webkit.WebView#findAddress(String)}
         */
        String findAddress(String addr);

        /**
         * Implements the API method:
         * {@link android.webkit.WebSettings#getDefaultUserAgent(Context) }
         */
        String getDefaultUserAgent(Context context);

        /**
         * Used for tests only.
         */
         void freeMemoryForTests();

        /**
         * Implements the API method:
         * {@link android.webkit.WebView#setWebContentsDebuggingEnabled(boolean) }
         */
        void setWebContentsDebuggingEnabled(boolean enable);

        /**
         * Implements the API method:
         * {@link android.webkit.WebView#clearClientCertPreferences(Runnable) }
         */
        void clearClientCertPreferences(Runnable onCleared);

        /**
         * Implements the API method:
         * {@link android.webkit.WebView#setSlowWholeDocumentDrawEnabled(boolean) }
         */
        void enableSlowWholeDocumentDraw();

        /**
         * Implement the API method
         * {@link android.webkit.WebChromeClient.FileChooserParams#parseResult(int, Intent)}
         */
        Uri[] parseFileChooserResult(int resultCode, Intent intent);

        /**
         * Implement the API method
         * {@link android.webkit.WebView#startSafeBrowsing(Context , ValueCallback<Boolean>)}
         */
        void initSafeBrowsing(Context context, ValueCallback<Boolean> callback);

        /**
        * Implement the API method
        * {@link android.webkit.WebView#setSafeBrowsingWhitelist(List<String>,
        * ValueCallback<Boolean>)}
        */
        void setSafeBrowsingWhitelist(List<String> hosts, ValueCallback<Boolean> callback);

        /**
         * Implement the API method
         * {@link android.webkit.WebView#getSafeBrowsingPrivacyPolicyUrl()}
         */
        @NonNull
        Uri getSafeBrowsingPrivacyPolicyUrl();
    }

    Statics getStatics();

    /**
     * Construct a new WebViewProvider.
     * @param webView the WebView instance bound to this implementation instance. Note it will not
     * necessarily be fully constructed at the point of this call: defer real initialization to
     * WebViewProvider.init().
     * @param privateAccess provides access into WebView internal methods.
     */
    WebViewProvider createWebView(WebView webView, WebView.PrivateAccess privateAccess);

    /**
     * Gets the singleton GeolocationPermissions instance for this WebView implementation. The
     * implementation must return the same instance on subsequent calls.
     * @return the single GeolocationPermissions instance.
     */
    GeolocationPermissions getGeolocationPermissions();

    /**
     * Gets the singleton CookieManager instance for this WebView implementation. The
     * implementation must return the same instance on subsequent calls.
     *
     * @return the singleton CookieManager instance
     */
    CookieManager getCookieManager();

    /**
     * Gets the TokenBindingService instance for this WebView implementation. The
     * implementation must return the same instance on subsequent calls.
     *
     * @deprecated this method only returns {@code null}
     * @return the TokenBindingService instance (which is always {@code null})
     */
    TokenBindingService getTokenBindingService();

    /**
     * Gets the TracingController instance for this WebView implementation. The
     * implementation must return the same instance on subsequent calls.
     *
     * @return the TracingController instance
     */
    TracingController getTracingController();

    /**
     * Gets the ServiceWorkerController instance for this WebView implementation. The
     * implementation must return the same instance on subsequent calls.
     *
     * @return the ServiceWorkerController instance
     */
    ServiceWorkerController getServiceWorkerController();

    /**
     * Gets the singleton WebIconDatabase instance for this WebView implementation. The
     * implementation must return the same instance on subsequent calls.
     *
     * @return the singleton WebIconDatabase instance
     */
    WebIconDatabase getWebIconDatabase();

    /**
     * Gets the singleton WebStorage instance for this WebView implementation. The
     * implementation must return the same instance on subsequent calls.
     *
     * @return the singleton WebStorage instance
     */
    WebStorage getWebStorage();

    /**
     * Gets the singleton WebViewDatabase instance for this WebView implementation. The
     * implementation must return the same instance on subsequent calls.
     *
     * @return the singleton WebViewDatabase instance
     */
    WebViewDatabase getWebViewDatabase(Context context);

    /**
     * Gets the default PacProcessor instance.
     * @return the PacProcessor instance
     */
    @NonNull
    default PacProcessor getPacProcessor() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Create a new PacProcessor instance.
     *
     * @param network a {@link Network} which needs to be associated
     * with the returned {@link PacProcessor}.
     * If {@code null} the method returns default {@link PacProcessor}.
     * @return the {@link PacProcessor} instance associated with {@link Network}.
     */
    @NonNull
    default PacProcessor createPacProcessor() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Gets the classloader used to load internal WebView implementation classes. This interface
     * should only be used by the WebView Support Library.
     */
    ClassLoader getWebViewClassLoader();
}
