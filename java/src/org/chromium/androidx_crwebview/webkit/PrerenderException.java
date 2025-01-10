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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Super class for all asynchronous exceptions produced by
 * {@link WebViewCompat} prerender operations.
 */
@WebViewCompat.ExperimentalUrlPrerender
public class PrerenderException extends Exception {

    public PrerenderException(@NonNull String error, @Nullable Throwable cause) {
        super(error, cause);
    }
}
