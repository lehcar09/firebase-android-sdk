// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.firebase.firestore;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.ComponentProvider;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Function;

/** Gives access to package private methods in integration tests. */
public final class AccessHelper {

  /** Makes the FirebaseFirestore constructor accessible. */
  public static FirebaseFirestore newFirebaseFirestore(
      Context context,
      DatabaseId databaseId,
      String persistenceKey,
      CredentialsProvider<User> authProvider,
      CredentialsProvider<String> appCheckProvider,
      Function<FirebaseFirestoreSettings, ComponentProvider> componentProviderFactory,
      FirebaseApp firebaseApp,
      FirebaseFirestore.InstanceRegistry instanceRegistry) {
    return new FirebaseFirestore(
        context,
        databaseId,
        persistenceKey,
        authProvider,
        appCheckProvider,
        componentProviderFactory,
        firebaseApp,
        instanceRegistry,
        null);
  }

  public static AsyncQueue getAsyncQueue(FirebaseFirestore firestore) {
    return firestore.clientProvider.getAsyncQueue();
  }
}
