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

import org.chromium.android_crwebview.webkit.WebSettings;
import org.chromium.android_crwebview.webkit.WebView;

import androidx.annotation.AnyThread;
import androidx.annotation.IntDef;
import androidx.annotation.RequiresFeature;
import androidx.annotation.RestrictTo;

import org.jspecify.annotations.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

/**
 * Manages settings state for all Service Workers. These settings are not tied to
 * the lifetime of any WebView because service workers can outlive WebView instances.
 * The settings are similar to {@link WebSettings} but only settings relevant to
 * Service Workers are supported.
 */
@AnyThread
public abstract class ServiceWorkerWebSettingsCompat {
    /**
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public ServiceWorkerWebSettingsCompat() {}

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @IntDef(value = {
            WebSettings.LOAD_DEFAULT,
            WebSettings.LOAD_CACHE_ELSE_NETWORK,
            WebSettings.LOAD_NO_CACHE,
            WebSettings.LOAD_CACHE_ONLY
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CacheMode {}

    /**
     * Overrides the way the cache is used.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#SERVICE_WORKER_CACHE_MODE}.
     *
     * @param mode the mode to use. One of {@link WebSettings#LOAD_DEFAULT},
     * {@link WebSettings#LOAD_CACHE_ELSE_NETWORK}, {@link WebSettings#LOAD_NO_CACHE}
     * or {@link WebSettings#LOAD_CACHE_ONLY}. The default value is
     * {@link WebSettings#LOAD_DEFAULT}.
     * @see WebSettings#setCacheMode
     * @see #getCacheMode
     */
    @RequiresFeature(name = WebViewFeature.SERVICE_WORKER_CACHE_MODE,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract void setCacheMode(@CacheMode int mode);

    /**
     * Gets the current setting for overriding the cache mode.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#SERVICE_WORKER_CACHE_MODE}.
     *
     * @return the current setting for overriding the cache mode
     * @see #setCacheMode
     */
    @RequiresFeature(name = WebViewFeature.SERVICE_WORKER_CACHE_MODE,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract @CacheMode int getCacheMode();

    /**
     * Enables or disables content URL access from Service Workers.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#SERVICE_WORKER_CONTENT_ACCESS}.
     *
     * @see WebSettings#setAllowContentAccess
     * @see #getAllowContentAccess
     */
    @RequiresFeature(name = WebViewFeature.SERVICE_WORKER_CONTENT_ACCESS,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract void setAllowContentAccess(boolean allow);

    /**
     * Gets whether Service Workers support content URL access.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#SERVICE_WORKER_CONTENT_ACCESS}.
     *
     * @see #setAllowContentAccess
     */
    @RequiresFeature(name = WebViewFeature.SERVICE_WORKER_CONTENT_ACCESS,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract boolean getAllowContentAccess();

    /**
     *
     * Enables or disables file access within Service Workers.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#SERVICE_WORKER_FILE_ACCESS}.
     *
     * @see WebSettings#setAllowFileAccess
     * @see #getAllowContentAccess
     */
    @RequiresFeature(name = WebViewFeature.SERVICE_WORKER_FILE_ACCESS,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract void setAllowFileAccess(boolean allow);

    /**
     * Gets whether Service Workers support file access.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#SERVICE_WORKER_FILE_ACCESS}.
     *
     * @see #setAllowFileAccess
     */
    @RequiresFeature(name = WebViewFeature.SERVICE_WORKER_FILE_ACCESS,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract boolean getAllowFileAccess();

    /**
     * Sets whether Service Workers should not load resources from the network.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#SERVICE_WORKER_BLOCK_NETWORK_LOADS}.
     *
     * @param flag {@code true} means block network loads by the Service Workers
     * @see WebSettings#setBlockNetworkLoads
     * @see #getBlockNetworkLoads
     */
    @RequiresFeature(name = WebViewFeature.SERVICE_WORKER_BLOCK_NETWORK_LOADS,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract void setBlockNetworkLoads(boolean flag);

    /**
     * Gets whether Service Workers are prohibited from loading any resources from the network.
     *
     * <p>
     * This method should only be called if
     * {@link WebViewFeature#isFeatureSupported(String)}
     * returns true for {@link WebViewFeature#SERVICE_WORKER_BLOCK_NETWORK_LOADS}.
     *
     * @return {@code true} if the Service Workers are not allowed to load any resources from the
     * network
     * @see #setBlockNetworkLoads
     */
    @RequiresFeature(name = WebViewFeature.SERVICE_WORKER_BLOCK_NETWORK_LOADS,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract boolean getBlockNetworkLoads();


    /**
     * Get the currently configured allow-list of origins, which is guaranteed to receive the
     * {@code X-Requested-With} HTTP header on requests from service workers.
     * <p>
     * Any origin <em>not</em> on this allow-list may not receive the header, depending on the
     * current installed WebView provider.
     * <p>
     * The format of the strings in the allow-list follows the origin rules of
     * {@link WebViewCompat#addWebMessageListener(WebView, String, Set, WebViewCompat.WebMessageListener)}.
     *
     * @return The configured set of allow-listed origins.
     * @see #setRequestedWithHeaderOriginAllowList(Set)
     * @see WebSettingsCompat#getRequestedWithHeaderOriginAllowList(WebSettings)
     */
    @RequiresFeature(name = WebViewFeature.REQUESTED_WITH_HEADER_ALLOW_LIST,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract @NonNull Set<String> getRequestedWithHeaderOriginAllowList();

    /**
     * Set an allow-list of origins to receive the {@code X-Requested-With} HTTP header from
     * service workers.
     * <p>
     * Historically, this header was sent on all requests from WebView, containing the
     * app package name of the embedding app. Depending on the version of installed WebView, this
     * may no longer be the case, as the header was deprecated in late 2022, and its use
     * discontinued.
     * <p>
     * Apps can use this method to restore the legacy behavior for servers that still rely on
     * the deprecated header, but it should not be used to identify the WebView to first-party
     * servers under the control of the app developer.
     * <p>
     * The format of the strings in the allow-list follows the origin rules of
     * {@link WebViewCompat#addWebMessageListener(WebView, String, Set, WebViewCompat.WebMessageListener)}.
     *
     * @param allowList Set of origins to allow-list.
     * @see WebSettingsCompat#setRequestedWithHeaderOriginAllowList(WebSettings, Set)
     * @throws IllegalArgumentException if the allow-list contains a malformed origin.
     */
    @RequiresFeature(name = WebViewFeature.REQUESTED_WITH_HEADER_ALLOW_LIST,
            enforcement = "androidx.webkit.WebViewFeature#isFeatureSupported")
    public abstract void setRequestedWithHeaderOriginAllowList(@NonNull Set<String> allowList);
}
