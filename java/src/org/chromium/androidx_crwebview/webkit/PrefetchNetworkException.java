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

package org.chromium.androidx_crwebview.webkit;

import org.jspecify.annotations.NonNull;

/**
 * Class for network & server related Exceptions produced by
 * {@link Profile} prefetch operations.
 */
@Profile.ExperimentalUrlPrefetch
public class PrefetchNetworkException extends PrefetchException {
    public static final int NO_HTTP_RESPONSE_STATUS_CODE = 0;

    /**
     * The HTTP response status code returned from the server, this is based on <a
     * href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">MDN web response codes</a>.
     */
    public final int httpResponseStatusCode;

    public PrefetchNetworkException(@NonNull String error) {
        this(error, NO_HTTP_RESPONSE_STATUS_CODE);
    }

    public PrefetchNetworkException(@NonNull String error, int httpResponseStatusCode) {
        super(error);
        this.httpResponseStatusCode = httpResponseStatusCode;
    }

    public PrefetchNetworkException(int httpResponseStatusCode) {
        super();
        this.httpResponseStatusCode = httpResponseStatusCode;
    }

    public PrefetchNetworkException() {
        this(NO_HTTP_RESPONSE_STATUS_CODE);
    }

}
