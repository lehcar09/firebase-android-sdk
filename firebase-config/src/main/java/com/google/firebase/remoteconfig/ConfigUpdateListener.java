// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
//
// You may obtain a copy of the License at
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.firebase.remoteconfig;

import androidx.annotation.NonNull;

/**
 * Event listener interface for real-time Remote Config updates. Implement {@code
 * ConfigUpdateListener} to call {@link
 * com.google.firebase.remoteconfig.FirebaseRemoteConfig#addOnConfigUpdateListener}.
 */
public interface ConfigUpdateListener {
  /**
   * Callback for when a new config version has been automatically fetched from the backend and has
   * changed from the activated config.
   *
   * @param configUpdate A {@link ConfigUpdate} with information about the updated config version,
   *     including the set of updated parameters.
   */
  void onUpdate(@NonNull ConfigUpdate configUpdate);

  /**
   * Callback for when an error occurs while listening for updates or fetching the latest version of
   * the config.
   *
   * @param error A {@link FirebaseRemoteConfigException} with information about the error.
   */
  void onError(@NonNull FirebaseRemoteConfigException error);
}
