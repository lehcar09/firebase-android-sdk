/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.firebase.crashlytics

import androidx.annotation.Keep
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.components.Component
import com.google.firebase.components.ComponentRegistrar

/** Returns the [FirebaseCrashlytics] instance of the default [FirebaseApp]. */
val Firebase.crashlytics: FirebaseCrashlytics
  get() = FirebaseCrashlytics.getInstance()

/** Associates all key-value parameters with the reports */
fun FirebaseCrashlytics.setCustomKeys(init: KeyValueBuilder.() -> Unit) =
  setCustomKeys(KeyValueBuilder().apply(init).build())

/** Records a non-fatal report to send to Crashlytics with additional custom keys */
fun FirebaseCrashlytics.recordException(throwable: Throwable, init: KeyValueBuilder.() -> Unit) =
  recordException(throwable, KeyValueBuilder().apply(init).build())

/** @suppress */
@Keep
internal class FirebaseCrashlyticsKtxRegistrar : ComponentRegistrar {
  override fun getComponents(): List<Component<*>> = listOf()

  companion object {}
}
