package com.jerryokafor.core.datastore

import com.jerryokafor.core.datastore.store.UserDataStore
import com.jerryokafor.core.datastore.store.UserSettingsStore
import com.jerryokafor.core.datastore.store.userDataStoreFileName
import kotlinx.serialization.json.Json
import java.io.File

fun createUserDatastore(json: Json): UserDataStore = UserDataStore(json = json) {
    File(System.getProperty("java.io.tmpdir"), userDataStoreFileName).absolutePath
}

fun createUserSettingsStore(json: Json): UserSettingsStore = UserSettingsStore(json = json) {
    File(System.getProperty("java.io.tmpdir"), userDataStoreFileName).absolutePath
}
