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

import org.chromium.androidx_crwebview.webkit.OutcomeReceiverCompat;
import org.chromium.androidx_crwebview.webkit.PrefetchException;
import org.chromium.androidx_crwebview.webkit.PrefetchNetworkException;

import org.chromium.support_lib_boundary.PrefetchExceptionBoundaryInterface;
import org.chromium.support_lib_boundary.PrefetchNetworkExceptionBoundaryInterface;
import org.chromium.support_lib_boundary.PrefetchOperationCallbackBoundaryInterface;
import org.chromium.support_lib_boundary.util.BoundaryInterfaceReflectionUtil;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationHandler;

public class PrefetchOperationCallbackAdapter {

    /**
     * Builds the PrefetchOperationCallback to send to the prefetch request.
     *
     * @param callback OutcomeReceiver to be triggered for the caller.
     * @return the built InvocationHandler
     */
    public static @NonNull /* PrefetchOperationCallback */ InvocationHandler buildInvocationHandler(
            @NonNull OutcomeReceiverCompat<@Nullable Void, @NonNull PrefetchException> callback) {
        PrefetchOperationCallbackBoundaryInterface operationCallback =
                new PrefetchOperationCallbackBoundaryInterface() {
                    @Override
                    public void onSuccess() {
                        callback.onResult(null);
                    }

                    @Override
                    public void onFailure(InvocationHandler failure) {
                        if (BoundaryInterfaceReflectionUtil.instanceOfInOwnClassLoader(failure,
                                PrefetchNetworkExceptionBoundaryInterface.class.getName())) {
                            callback.onError(getNetworkException(failure));
                        } else {
                            callback.onError(getPrefetchException(failure));
                        }
                    }
                };

        return BoundaryInterfaceReflectionUtil.createInvocationHandlerFor(
                operationCallback);
    }

    private static PrefetchNetworkException getNetworkException(InvocationHandler error) {
        PrefetchNetworkExceptionBoundaryInterface failure =
                BoundaryInterfaceReflectionUtil.castToSuppLibClass(
                        PrefetchNetworkExceptionBoundaryInterface.class, error);
        if (failure.getMessage() != null) {
            return new PrefetchNetworkException(failure.getMessage(),
                    failure.getHttpResponseStatusCode());
        } else {
            return new PrefetchNetworkException(failure.getHttpResponseStatusCode());
        }
    }

    private static PrefetchException getPrefetchException(InvocationHandler error) {
        PrefetchExceptionBoundaryInterface failure =
                BoundaryInterfaceReflectionUtil.castToSuppLibClass(
                        PrefetchExceptionBoundaryInterface.class, error);
        if (failure.getMessage() != null) {
            return new PrefetchException(failure.getMessage());
        } else {
            return new PrefetchException();
        }
    }
}
