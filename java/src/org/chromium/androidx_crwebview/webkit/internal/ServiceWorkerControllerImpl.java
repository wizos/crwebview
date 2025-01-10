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

import org.chromium.android_crwebview.webkit.ServiceWorkerController;

import androidx.annotation.RequiresApi;
import org.chromium.androidx_crwebview.webkit.ServiceWorkerClientCompat;
import org.chromium.androidx_crwebview.webkit.ServiceWorkerControllerCompat;
import org.chromium.androidx_crwebview.webkit.ServiceWorkerWebSettingsCompat;

import org.chromium.support_lib_boundary.ServiceWorkerControllerBoundaryInterface;
import org.chromium.support_lib_boundary.util.BoundaryInterfaceReflectionUtil;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Implementation of {@link ServiceWorkerControllerCompat}.
 * This class uses either the framework, the WebView APK, or both, to implement
 * {@link ServiceWorkerControllerCompat} functionality.
 */
public class ServiceWorkerControllerImpl extends ServiceWorkerControllerCompat {
    private ServiceWorkerController mFrameworksImpl;
    private ServiceWorkerControllerBoundaryInterface mBoundaryInterface;
    private final ServiceWorkerWebSettingsCompat mWebSettings;

    public ServiceWorkerControllerImpl() {
        final ApiFeature.N feature = WebViewFeatureInternal.SERVICE_WORKER_BASIC_USAGE;
        if (feature.isSupportedByFramework()) {
            mFrameworksImpl = ApiHelperForN.getServiceWorkerControllerInstance();
            // The current WebView APK might not be compatible with the support library, so set the
            // boundary interface to null for now.
            mBoundaryInterface = null;
            mWebSettings = ApiHelperForN.getServiceWorkerWebSettingsImpl(getFrameworksImpl());
        } else if (feature.isSupportedByWebView()) {
            mFrameworksImpl = null;
            mBoundaryInterface = WebViewGlueCommunicator.getFactory().getServiceWorkerController();
            mWebSettings = new ServiceWorkerWebSettingsImpl(
                    mBoundaryInterface.getServiceWorkerWebSettings());
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }

    @RequiresApi(24)
    private ServiceWorkerController getFrameworksImpl() {
        if (mFrameworksImpl == null) {
            mFrameworksImpl = ApiHelperForN.getServiceWorkerControllerInstance();
        }
        return mFrameworksImpl;
    }

    private ServiceWorkerControllerBoundaryInterface getBoundaryInterface() {
        if (mBoundaryInterface == null) {
            mBoundaryInterface = WebViewGlueCommunicator.getFactory().getServiceWorkerController();
        }
        return mBoundaryInterface;
    }

    @Override
    public @NonNull ServiceWorkerWebSettingsCompat getServiceWorkerWebSettings() {
        return mWebSettings;
    }

    @Override
    public void setServiceWorkerClient(@Nullable ServiceWorkerClientCompat client)  {
        final ApiFeature.N feature = WebViewFeatureInternal.SERVICE_WORKER_BASIC_USAGE;
        if (feature.isSupportedByFramework()) {
            if (client == null) {
                ApiHelperForN.setServiceWorkerClient(getFrameworksImpl(), null);
            } else {
                ApiHelperForN.setServiceWorkerClientCompat(getFrameworksImpl(), client);
            }
        } else if (feature.isSupportedByWebView()) {
            if (client == null) {
                getBoundaryInterface().setServiceWorkerClient(null);
            } else {
                getBoundaryInterface().setServiceWorkerClient(
                        BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(
                                new ServiceWorkerClientAdapter(client)));
            }
        } else {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
    }
}
