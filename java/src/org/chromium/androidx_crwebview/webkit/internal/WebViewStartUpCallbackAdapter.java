/*
 * Copyright 2024 The Android Open Source Project
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

import androidx.annotation.NonNull;
import org.chromium.androidx_crwebview.webkit.BlockingStartUpLocation;
import org.chromium.androidx_crwebview.webkit.WebViewCompat;
import org.chromium.androidx_crwebview.webkit.WebViewStartUpResult;

import org.chromium.support_lib_boundary.WebViewStartUpCallbackBoundaryInterface;
import org.chromium.support_lib_boundary.WebViewStartUpResultBoundaryInterface;
import org.chromium.support_lib_boundary.util.BoundaryInterfaceReflectionUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Adapter between WebViewCompat.WebViewStartUpCallback and WebViewStartUpCallbackBoundaryInterface
 * (the corresponding interface shared with the support library glue in the WebView APK).
 */
public class WebViewStartUpCallbackAdapter implements WebViewStartUpCallbackBoundaryInterface {
    private final WebViewCompat.WebViewStartUpCallback mWebViewStartUpCallback;

    public WebViewStartUpCallbackAdapter(
            @NonNull WebViewCompat.WebViewStartUpCallback webViewStartUpCallback) {
        mWebViewStartUpCallback = webViewStartUpCallback;
    }

    /**
     * Adapter method for
     * {@link androidx.webkit.WebViewCompat.WebViewStartUpCallback#onSuccess(WebViewStartUpResult)}.
     */
    @Override
    public void onSuccess(@NonNull InvocationHandler resultInvocationHandler) {
        final WebViewStartUpResult result = webViewStartUpResultFromBoundaryInterface(
                Objects.requireNonNull(BoundaryInterfaceReflectionUtil.castToSuppLibClass(
                        WebViewStartUpResultBoundaryInterface.class, resultInvocationHandler)));
        mWebViewStartUpCallback.onSuccess(result);
    }

    private static class BlockingStartUpLocationImpl implements BlockingStartUpLocation {
        private final Throwable mThrowable;

        BlockingStartUpLocationImpl(Throwable t) {
            mThrowable = t;
        }

        /**
         * Gets the stack information depicting the code location.
         */
        @Override
        public String getStackInformation() {
            StringWriter sw = new StringWriter();
            mThrowable.printStackTrace(new PrintWriter(sw));
            return sw.toString();
        }
    }

    private WebViewStartUpResult webViewStartUpResultFromBoundaryInterface(
            @NonNull WebViewStartUpResultBoundaryInterface result) {
        return new WebViewStartUpResult() {
            private final List<BlockingStartUpLocation> mBlockingStartUpLocations =
                    convertFromThrowables(result.getBlockingStartUpLocations());

            @Override
            public Long getTotalTimeInUiThreadMillis() {
                return result.getTotalTimeInUiThreadMillis();
            }

            @Override
            public Long getMaxTimePerTaskInUiThreadMillis() {
                return result.getMaxTimePerTaskInUiThreadMillis();
            }

            @Override
            public List<BlockingStartUpLocation> getBlockingStartUpLocations() {
                return mBlockingStartUpLocations;
            }

            private List<BlockingStartUpLocation> convertFromThrowables(
                    List<Throwable> throwables) {
                List<BlockingStartUpLocation> blockingStartUpLocations = new ArrayList<>();
                for (Throwable location: throwables) {
                    blockingStartUpLocations.add(new BlockingStartUpLocationImpl(location));
                }
                return blockingStartUpLocations;
            }
        };
    }
}
