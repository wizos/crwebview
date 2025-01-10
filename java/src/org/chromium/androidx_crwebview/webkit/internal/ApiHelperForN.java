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
import android.os.Build;
import org.chromium.android_crwebview.webkit.ServiceWorkerClient;
import org.chromium.android_crwebview.webkit.ServiceWorkerController;
import org.chromium.android_crwebview.webkit.ServiceWorkerWebSettings;
import org.chromium.android_crwebview.webkit.WebResourceRequest;
import org.chromium.android_crwebview.webkit.WebSettings;

import androidx.annotation.RequiresApi;
import org.chromium.androidx_crwebview.webkit.ServiceWorkerClientCompat;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.File;

/**
 * Utility class to use new APIs that were added in N (API level 24).
 * These need to exist in a separate class so that Android framework can successfully verify
 * classes without encountering the new APIs.
 */
@RequiresApi(Build.VERSION_CODES.N)
public class ApiHelperForN {
    private ApiHelperForN() {
    }

    /**
     * @see Context#getDataDir()
     */
    public static @NonNull File getDataDir(@NonNull Context context) {
        return context.getDataDir();
    }

    /**
     * @see ServiceWorkerController#getInstance()
     */
    public static @NonNull ServiceWorkerController getServiceWorkerControllerInstance() {
        return ServiceWorkerController.getInstance();
    }

    /**
     * @see ServiceWorkerController#getServiceWorkerWebSettings()
     */
    public static @NonNull ServiceWorkerWebSettings getServiceWorkerWebSettings(
            @NonNull ServiceWorkerController serviceWorkerController) {
        return serviceWorkerController.getServiceWorkerWebSettings();
    }

    /**
     * @see ServiceWorkerController#getServiceWorkerWebSettings()
     */
    public static @NonNull ServiceWorkerWebSettingsImpl getServiceWorkerWebSettingsImpl(
            @NonNull ServiceWorkerController serviceWorkerController) {
        return new ServiceWorkerWebSettingsImpl(
                getServiceWorkerWebSettings(serviceWorkerController));
    }

    /**
     * @see ServiceWorkerController#setServiceWorkerClient(ServiceWorkerClient)
     */
    public static void setServiceWorkerClient(
            @NonNull ServiceWorkerController serviceWorkerController,
            @Nullable ServiceWorkerClient serviceWorkerClient) {
        serviceWorkerController.setServiceWorkerClient(serviceWorkerClient);
    }

    /**
     * @see ServiceWorkerController#setServiceWorkerClient(ServiceWorkerClient)
     */
    public static void setServiceWorkerClientCompat(
            @NonNull ServiceWorkerController serviceWorkerController,
            @NonNull ServiceWorkerClientCompat serviceWorkerClientCompat) {
        serviceWorkerController.setServiceWorkerClient(
                new FrameworkServiceWorkerClient(serviceWorkerClientCompat));
    }

    /**
     * @see ServiceWorkerWebSettings#setCacheMode(int)
     */
    public static void setCacheMode(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings,
            int cacheMode) {
        serviceWorkerWebSettings.setCacheMode(cacheMode);
    }

    /**
     * @see ServiceWorkerWebSettings#getCacheMode()
     */
    public static int getCacheMode(@NonNull ServiceWorkerWebSettings serviceWorkerWebSettings) {
        return serviceWorkerWebSettings.getCacheMode();
    }

    /**
     * @see ServiceWorkerWebSettings#setAllowContentAccess(boolean)
     */
    public static void setAllowContentAccess(
            @NonNull ServiceWorkerWebSettings serviceWorkerWebSettings,
            boolean allowContentAccess) {
        serviceWorkerWebSettings.setAllowContentAccess(allowContentAccess);
    }

    /**
     * @see ServiceWorkerWebSettings#getAllowContentAccess()
     */
    public static boolean getAllowContentAccess(
            @NonNull ServiceWorkerWebSettings serviceWorkerWebSettings) {
        return serviceWorkerWebSettings.getAllowContentAccess();
    }

    /**
     * @see ServiceWorkerWebSettings#setAllowFileAccess(boolean)
     */
    public static void setAllowFileAccess(
            @NonNull ServiceWorkerWebSettings serviceWorkerWebSettings, boolean allowFileAccess) {
        serviceWorkerWebSettings.setAllowFileAccess(allowFileAccess);
    }

    /**
     * @see ServiceWorkerWebSettings#getAllowFileAccess()
     */
    public static boolean getAllowFileAccess(
            @NonNull ServiceWorkerWebSettings serviceWorkerWebSettings) {
        return serviceWorkerWebSettings.getAllowFileAccess();
    }

    /**
     * @see ServiceWorkerWebSettings#setBlockNetworkLoads(boolean)
     */
    public static void setBlockNetworkLoads(
            @NonNull ServiceWorkerWebSettings serviceWorkerWebSettings, boolean blockNetworkLoads) {
        serviceWorkerWebSettings.setBlockNetworkLoads(blockNetworkLoads);
    }

    /**
     * @see ServiceWorkerWebSettings#getBlockNetworkLoads()
     */
    public static boolean getBlockNetworkLoads(
            @NonNull ServiceWorkerWebSettings serviceWorkerWebSettings) {
        return serviceWorkerWebSettings.getBlockNetworkLoads();
    }

    /**
     * @see WebResourceRequest#isRedirect()
     */
    public static boolean isRedirect(@NonNull WebResourceRequest webResourceRequest) {
        return webResourceRequest.isRedirect();
    }

    /**
     * @see WebSettings#setDisabledActionModeMenuItems(int)
     */
    public static void setDisabledActionModeMenuItems(@NonNull WebSettings webSettings, int i) {
        webSettings.setDisabledActionModeMenuItems(i);
    }

    /**
     * @see WebSettings#getDisabledActionModeMenuItems()
     */
    public static int getDisabledActionModeMenuItems(@NonNull WebSettings webSettings) {
        return webSettings.getDisabledActionModeMenuItems();
    }
}
