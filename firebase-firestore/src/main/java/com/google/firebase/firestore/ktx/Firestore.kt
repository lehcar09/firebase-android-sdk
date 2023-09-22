// Copyright 2019 Google LLC
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

package com.google.firebase.firestore.ktx

import androidx.annotation.Keep
import com.google.firebase.FirebaseApp
import com.google.firebase.components.Component
import com.google.firebase.components.ComponentRegistrar
import com.google.firebase.firestore.*
import com.google.firebase.firestore.util.Executors.BACKGROUND_EXECUTOR
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

/** Returns the [FirebaseFirestore] instance of the default [FirebaseApp]. */
@Deprecated(
  "Use `com.google.firebase.Firebase.firestore` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "Firebase.firestore",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.firestore.firestore"]
  )
)
val Firebase.firestore: FirebaseFirestore
  get() = FirebaseFirestore.getInstance()

/** Returns the [FirebaseFirestore] instance of a given [FirebaseApp]. */
@Deprecated(
  "Use `com.google.firebase.Firebase.firestore(app)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "Firebase.firestore(app)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.firestore.firestore"]
  )
)
fun Firebase.firestore(app: FirebaseApp): FirebaseFirestore = FirebaseFirestore.getInstance(app)

/** Returns the [FirebaseFirestore] instance of a given [FirebaseApp] and database name. */
@Deprecated(
  "Use `com.google.firebase.Firebase.firestore(app, database)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "Firebase.firestore(app, database)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.firestore.firestore"]
  )
)
fun Firebase.firestore(app: FirebaseApp, database: String): FirebaseFirestore =
  FirebaseFirestore.getInstance(app, database)

/**
 * Returns the [FirebaseFirestore] instance of the default [FirebaseApp], given the database name.
 */
@Deprecated(
  "Use `com.google.firebase.Firebase.firestore(database)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "Firebase.firestore(database)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.firestore.firestore"]
  )
)
fun Firebase.firestore(database: String): FirebaseFirestore =
  FirebaseFirestore.getInstance(database)

/**
 * Returns the contents of the document converted to a POJO or null if the document doesn't exist.
 *
 * @param T The type of the object to create.
 * @return The contents of the document in an object of type T or null if the document doesn't
 * ```
 *     exist.
 * ```
 */
@Deprecated(
  "Use `com.google.firebase.firestore.DocumentSnapshot.toObject` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "DocumentSnapshot.toObject",
    imports =
      ["com.google.firebase.Firebase", "com.google.firebase.firestore.DocumentSnapshot.toObject"]
  )
)
inline fun <reified T> DocumentSnapshot.toObject(): T? = toObject(T::class.java)

/**
 * Returns the contents of the document converted to a POJO or null if the document doesn't exist.
 *
 * @param T The type of the object to create.
 * @param serverTimestampBehavior Configures the behavior for server timestamps that have not yet
 * ```
 *     been set to their final value.
 * @return
 * ```
 * The contents of the document in an object of type T or null if the document doesn't
 * ```
 *     exist.
 * ```
 */
@Deprecated(
  "Use `com.google.firebase.firestore.DocumentSnapshot.toObject(serverTimestampBehavior).` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "DocumentSnapshot.toObject(serverTimestampBehavior)",
    imports =
      ["com.google.firebase.Firebase", "com.google.firebase.firestore.DocumentSnapshot.toObject"]
  )
)
inline fun <reified T> DocumentSnapshot.toObject(
  serverTimestampBehavior: DocumentSnapshot.ServerTimestampBehavior
): T? = toObject(T::class.java, serverTimestampBehavior)

/**
 * Returns the value at the field, converted to a POJO, or null if the field or document doesn't
 * exist.
 *
 * @param field The path to the field.
 * @param T The type to convert the field value to.
 * @return The value at the given field or null.
 */
@Deprecated(
  "Use `com.google.firebase.firestore.DocumentSnapshot.getField(field)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "DocumentSnapshot.getField(field)",
    imports =
      ["com.google.firebase.Firebase", "com.google.firebase.firestore.DocumentSnapshot.getField"]
  )
)
inline fun <reified T> DocumentSnapshot.getField(field: String): T? = get(field, T::class.java)

/**
 * Returns the value at the field, converted to a POJO, or null if the field or document doesn't
 * exist.
 *
 * @param field The path to the field.
 * @param T The type to convert the field value to.
 * @param serverTimestampBehavior Configures the behavior for server timestamps that have not yet
 * ```
 *     been set to their final value.
 * @return
 * ```
 * The value at the given field or null.
 */
@Deprecated(
  "Use `com.google.firebase.firestore.DocumentSnapshot.getField(field, serverTimestampBehavior) ` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "DocumentSnapshot.getField(field, serverTimestampBehavior)",
    imports =
      ["com.google.firebase.Firebase", "com.google.firebase.firestore.DocumentSnapshot.getField"]
  )
)
inline fun <reified T> DocumentSnapshot.getField(
  field: String,
  serverTimestampBehavior: DocumentSnapshot.ServerTimestampBehavior
): T? = get(field, T::class.java, serverTimestampBehavior)

/**
 * Returns the value at the field, converted to a POJO, or null if the field or document doesn't
 * exist.
 *
 * @param fieldPath The path to the field.
 * @param T The type to convert the field value to.
 * @return The value at the given field or null.
 */
@Deprecated(
  "Use `com.google.firebase.firestore.DocumentSnapshot.getField(fieldPath) ` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "DocumentSnapshot.getField(fieldPath)",
    imports =
      ["com.google.firebase.Firebase", "com.google.firebase.firestore.DocumentSnapshot.getField"]
  )
)
inline fun <reified T> DocumentSnapshot.getField(fieldPath: FieldPath): T? =
  get(fieldPath, T::class.java)

/**
 * Returns the value at the field, converted to a POJO, or null if the field or document doesn't
 * exist.
 *
 * @param fieldPath The path to the field.
 * @param T The type to convert the field value to.
 * @param serverTimestampBehavior Configures the behavior for server timestamps that have not yet
 * ```
 *     been set to their final value.
 * @return
 * ```
 * The value at the given field or null.
 */
@Deprecated(
  "Use `com.google.firebase.firestore.DocumentSnapshot.getField(fieldPath, serverTimestampBehavior) ` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "DocumentSnapshot.getField(fieldPath, serverTimestampBehavior)",
    imports =
      ["com.google.firebase.Firebase", "com.google.firebase.firestore.DocumentSnapshot.getField"]
  )
)
inline fun <reified T> DocumentSnapshot.getField(
  fieldPath: FieldPath,
  serverTimestampBehavior: DocumentSnapshot.ServerTimestampBehavior
): T? = get(fieldPath, T::class.java, serverTimestampBehavior)

/**
 * Returns the contents of the document converted to a POJO.
 *
 * @param T The type of the object to create.
 * @return The contents of the document in an object of type T.
 */
@Deprecated(
  "Use `com.google.firebase.firestore.QueryDocumentSnapshot.toObject` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "QueryDocumentSnapshot.toObject",
    imports =
      [
        "com.google.firebase.Firebase",
        "com.google.firebase.firestore.QueryDocumentSnapshot.toObject"
      ]
  )
)
inline fun <reified T : Any> QueryDocumentSnapshot.toObject(): T = toObject(T::class.java)

/**
 * Returns the contents of the document converted to a POJO.
 *
 * @param T The type of the object to create.
 * @param serverTimestampBehavior Configures the behavior for server timestamps that have not yet
 * ```
 *     been set to their final value.
 * @return
 * ```
 * The contents of the document in an object of type T.
 */
@Deprecated(
  "Use `com.google.firebase.firestore.QueryDocumentSnapshot.toObject(serverTimestampBehavior)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "QueryDocumentSnapshot.toObject(serverTimestampBehavior)",
    imports =
      [
        "com.google.firebase.Firebase",
        "com.google.firebase.firestore.QueryDocumentSnapshot.toObject"
      ]
  )
)
inline fun <reified T : Any> QueryDocumentSnapshot.toObject(
  serverTimestampBehavior: DocumentSnapshot.ServerTimestampBehavior
): T = toObject(T::class.java, serverTimestampBehavior)

/**
 * Returns the contents of the documents in the QuerySnapshot, converted to the provided class, as a
 * list.
 *
 * @param T The POJO type used to convert the documents in the list.
 */
@Deprecated(
  "Use `com.google.firebase.firestore.QuerySnapshot.toObjects` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "QuerySnapshot.toObjects",
    imports =
      ["com.google.firebase.Firebase", "com.google.firebase.firestore.QuerySnapshot.toObjects"]
  )
)
inline fun <reified T : Any> QuerySnapshot.toObjects(): List<T> = toObjects(T::class.java)

/**
 * Returns the contents of the documents in the QuerySnapshot, converted to the provided class, as a
 * list.
 *
 * @param T The POJO type used to convert the documents in the list.
 * @param serverTimestampBehavior Configures the behavior for server timestamps that have not yet
 * ```
 *     been set to their final value.
 * ```
 */
@Deprecated(
  "Use `com.google.firebase.firestore.QuerySnapshot.toObjects(serverTimestampBehavior)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "QuerySnapshot.toObjects(serverTimestampBehavior)",
    imports =
      ["com.google.firebase.Firebase", "com.google.firebase.firestore.QuerySnapshot.toObjects"]
  )
)
inline fun <reified T : Any> QuerySnapshot.toObjects(
  serverTimestampBehavior: DocumentSnapshot.ServerTimestampBehavior
): List<T> = toObjects(T::class.java, serverTimestampBehavior)

/** Returns a [FirebaseFirestoreSettings] instance initialized using the [init] function. */
@Deprecated(
  "Use `com.google.firebase.firestore.firestoreSettings(init)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "firestoreSettings(init)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.firestore.firestoreSettings"]
  )
)
fun firestoreSettings(
  init: FirebaseFirestoreSettings.Builder.() -> Unit
): FirebaseFirestoreSettings {
  val builder = FirebaseFirestoreSettings.Builder()
  builder.init()
  return builder.build()
}

fun memoryCacheSettings(init: MemoryCacheSettings.Builder.() -> Unit): MemoryCacheSettings {
  val builder = MemoryCacheSettings.newBuilder()
  builder.init()
  return builder.build()
}

fun memoryEagerGcSettings(init: MemoryEagerGcSettings.Builder.() -> Unit): MemoryEagerGcSettings {
  val builder = MemoryEagerGcSettings.newBuilder()
  builder.init()
  return builder.build()
}

fun memoryLruGcSettings(init: MemoryLruGcSettings.Builder.() -> Unit): MemoryLruGcSettings {
  val builder = MemoryLruGcSettings.newBuilder()
  builder.init()
  return builder.build()
}

fun persistentCacheSettings(
  init: PersistentCacheSettings.Builder.() -> Unit
): PersistentCacheSettings {
  val builder = PersistentCacheSettings.newBuilder()
  builder.init()
  return builder.build()
}

/** @suppress */
@Deprecated(
  "Use `com.google.firebase.firestore.FirebaseFirestoreKtxRegistrar` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "FirebaseFirestoreKtxRegistrar",
    imports =
      [
        "com.google.firebase.Firebase",
        "com.google.firebase.firestore.FirebaseFirestoreKtxRegistrar"
      ]
  )
)
@Keep
class FirebaseFirestoreKtxRegistrar : ComponentRegistrar {
  override fun getComponents(): List<Component<*>> = listOf()
}

/**
 * Starts listening to the document referenced by this `DocumentReference` with the given options
 * and emits its values via a [Flow].
 *
 * - When the returned flow starts being collected, an [EventListener] will be attached.
 * - When the flow completes, the listener will be removed.
 *
 * @param metadataChanges controls metadata-only changes. Default: [MetadataChanges.EXCLUDE]
 */
@Deprecated(
  "com.google.firebase.firestorektx.DocumentReference.snapshots(metadataChanges) has been deprecated. Use `com.google.firebase.firestoreDocumentReference.snapshots(` instead.",
  ReplaceWith(
    expression = "DocumentReference.snapshots(metadataChanges)",
    imports =
      ["com.google.firebase.Firebase", "com.google.firebase.firestore.DocumentReference.snapshots"]
  )
)
fun DocumentReference.snapshots(
  metadataChanges: MetadataChanges = MetadataChanges.EXCLUDE
): Flow<DocumentSnapshot> {
  return callbackFlow {
    val registration =
      addSnapshotListener(BACKGROUND_EXECUTOR, metadataChanges) { snapshot, exception ->
        if (exception != null) {
          cancel(message = "Error getting DocumentReference snapshot", cause = exception)
        } else if (snapshot != null) {
          trySendBlocking(snapshot)
        }
      }
    awaitClose { registration.remove() }
  }
}

/**
 * Starts listening to this query with the given options and emits its values via a [Flow].
 *
 * - When the returned flow starts being collected, an [EventListener] will be attached.
 * - When the flow completes, the listener will be removed.
 *
 * @param metadataChanges controls metadata-only changes. Default: [MetadataChanges.EXCLUDE]
 */
@Deprecated(
  "Use `com.google.firebase.firestore.Query.snapshots(metadataChanges)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "Query.snapshots(metadataChanges)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.firestore.Query.snapshots"]
  )
)
fun Query.snapshots(
  metadataChanges: MetadataChanges = MetadataChanges.EXCLUDE
): Flow<QuerySnapshot> {
  return callbackFlow {
    val registration =
      addSnapshotListener(BACKGROUND_EXECUTOR, metadataChanges) { snapshot, exception ->
        if (exception != null) {
          cancel(message = "Error getting Query snapshot", cause = exception)
        } else if (snapshot != null) {
          trySendBlocking(snapshot)
        }
      }
    awaitClose { registration.remove() }
  }
}

/**
 * Starts listening to this query with the given options and emits its values converted to a POJO
 * via a [Flow].
 *
 * - When the returned flow starts being collected, an [EventListener] will be attached.
 * - When the flow completes, the listener will be removed.
 *
 * @param metadataChanges controls metadata-only changes. Default: [MetadataChanges.EXCLUDE]
 * @param T The type of the object to convert to.
 */
@Deprecated(
  "Use `com.google.firebase.firestore.Query.dataObjects(metadataChanges)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "Query.dataObjects(metadataChanges)",
    imports = ["com.google.firebase.Firebase", "com.google.firebase.firestore.Query.dataObjects"]
  )
)
inline fun <reified T : Any> Query.dataObjects(
  metadataChanges: MetadataChanges = MetadataChanges.EXCLUDE
): Flow<List<T>> = snapshots(metadataChanges).map { it.toObjects(T::class.java) }

/**
 * Starts listening to the document referenced by this `DocumentReference` with the given options
 * and emits its values converted to a POJO via a [Flow].
 *
 * - When the returned flow starts being collected, an [EventListener] will be attached.
 * - When the flow completes, the listener will be removed.
 *
 * @param metadataChanges controls metadata-only changes. Default: [MetadataChanges.EXCLUDE]
 * @param T The type of the object to convert to.
 */
@Deprecated(
  "Use `com.google.firebase.firestore.DocumentReference.dataObjects(metadataChanges)` from the main module instead. The Kotlin extensions (KTX) APIs have been added to their respective main modules, and the Kotlin extension (KTX) APIs in `com.google.firebase.firebase-database-ktx` are now deprecated. As early as April 2024, we'll no longer release KTX modules. For details, see the <a href=\"https://firebase.google.com/docs/android/ktx-apis-to-main-modules\">FAQ about this initiative.</a>",
  ReplaceWith(
    expression = "DocumentReference.dataObjects(metadataChanges)",
    imports =
      [
        "com.google.firebase.Firebase",
        "com.google.firebase.firestore.DocumentReference.dataObjects"
      ]
  )
)
inline fun <reified T : Any> DocumentReference.dataObjects(
  metadataChanges: MetadataChanges = MetadataChanges.EXCLUDE
): Flow<T?> = snapshots(metadataChanges).map { it.toObject(T::class.java) }