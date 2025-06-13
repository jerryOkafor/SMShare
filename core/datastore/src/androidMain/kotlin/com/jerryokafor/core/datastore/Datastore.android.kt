package com.jerryokafor.core.datastore

import android.content.Context
import com.jerryokafor.core.datastore.store.UserDataStore
import com.jerryokafor.core.datastore.store.UserSettingsStore
import com.jerryokafor.core.datastore.store.userDataStoreFileName
import com.jerryokafor.core.datastore.store.userSettingsStoreFileName
import kotlinx.serialization.json.Json

fun createUserDataStore(
    context: Context,
    json: Json,
): UserDataStore = UserDataStore(json) {
    context.filesDir.resolve(userDataStoreFileName).absolutePath
}

fun createUserSettingsStore(
    context: Context,
    json: Json,
): UserSettingsStore = UserSettingsStore(json) {
    context.filesDir.resolve(userSettingsStoreFileName).absolutePath
}
