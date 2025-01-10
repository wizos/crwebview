/*
 * Copyright 2023 The Android Open Source Project
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

import android.os.CancellationSignal;
import org.chromium.android_crwebview.webkit.CookieManager;
import org.chromium.android_crwebview.webkit.GeolocationPermissions;
import org.chromium.android_crwebview.webkit.ServiceWorkerController;
import org.chromium.android_crwebview.webkit.WebStorage;

import org.chromium.androidx_crwebview.webkit.OutcomeReceiverCompat;
import org.chromium.androidx_crwebview.webkit.PrefetchException;
import org.chromium.androidx_crwebview.webkit.Profile;
import org.chromium.androidx_crwebview.webkit.SpeculativeLoadingParameters;

import org.chromium.support_lib_boundary.ProfileBoundaryInterface;
import org.chromium.support_lib_boundary.util.BoundaryInterfaceReflectionUtil;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationHandler;
import java.util.concurrent.Executor;


/**
 * Internal implementation of Profile.
 */
public class ProfileImpl implements Profile {

    private final ProfileBoundaryInterface mProfileImpl;

    ProfileImpl(@NonNull ProfileBoundaryInterface profileImpl) {
        mProfileImpl = profileImpl;
    }

    // Use ProfileStore to create a Profile instance.
    private ProfileImpl() {
        mProfileImpl = null;
    }

    @Override
    public @NonNull String getName() {
        ApiFeature.NoFramework feature = WebViewFeatureInternal.MULTI_PROFILE;
        if (feature.isSupportedByWebView()) {
            return mProfileImpl.getName();
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    @Override
    public @NonNull CookieManager getCookieManager() throws IllegalStateException {
        ApiFeature.NoFramework feature = WebViewFeatureInternal.MULTI_PROFILE;
        if (feature.isSupportedByWebView()) {
            return mProfileImpl.getCookieManager();
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    @Override
    public @NonNull WebStorage getWebStorage() throws IllegalStateException {
        ApiFeature.NoFramework feature = WebViewFeatureInternal.MULTI_PROFILE;
        if (feature.isSupportedByWebView()) {
            return mProfileImpl.getWebStorage();
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    @Override
    public @NonNull GeolocationPermissions getGeolocationPermissions()
            throws IllegalStateException {
        ApiFeature.NoFramework feature = WebViewFeatureInternal.MULTI_PROFILE;
        if (feature.isSupportedByWebView()) {
            return mProfileImpl.getGeoLocationPermissions();
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    @Override
    public @NonNull ServiceWorkerController getServiceWorkerController()
            throws IllegalStateException {
        ApiFeature.NoFramework feature = WebViewFeatureInternal.MULTI_PROFILE;
        if (feature.isSupportedByWebView()) {
            return mProfileImpl.getServiceWorkerController();
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    @Override
    public void prefetchUrlAsync(@NonNull String url,
            @Nullable CancellationSignal cancellationSignal,
            @NonNull Executor callbackExecutor,
            @NonNull SpeculativeLoadingParameters params,
            @NonNull OutcomeReceiverCompat<Void, PrefetchException> callback) {
        ApiFeature.NoFramework feature = WebViewFeatureInternal.PROFILE_URL_PREFETCH;
        if (feature.isSupportedByWebView()) {
            InvocationHandler paramsBoundaryInterface =
                    BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(
                            new SpeculativeLoadingParametersAdapter(params));

            mProfileImpl.prefetchUrl(url, cancellationSignal, callbackExecutor,
                    paramsBoundaryInterface,
                    PrefetchOperationCallbackAdapter.buildInvocationHandler(callback));

        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    @Override
    public void prefetchUrlAsync(@NonNull String url,
            @Nullable CancellationSignal cancellationSignal,
            @NonNull Executor callbackExecutor,
            @NonNull OutcomeReceiverCompat<Void, PrefetchException> callback) {
        ApiFeature.NoFramework feature = WebViewFeatureInternal.PROFILE_URL_PREFETCH;
        if (feature.isSupportedByWebView()) {
            mProfileImpl.prefetchUrl(url, cancellationSignal, callbackExecutor,
                    PrefetchOperationCallbackAdapter.buildInvocationHandler(callback));
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    @Override
    public void clearPrefetchAsync(@NonNull String url,
            @NonNull Executor callbackExecutor,
            @NonNull OutcomeReceiverCompat<Void, PrefetchException> callback) {
        ApiFeature.NoFramework feature = WebViewFeatureInternal.PROFILE_URL_PREFETCH;
        if (feature.isSupportedByWebView()) {
            mProfileImpl.clearPrefetch(url, callbackExecutor,
                    PrefetchOperationCallbackAdapter.buildInvocationHandler(callback));
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

}
