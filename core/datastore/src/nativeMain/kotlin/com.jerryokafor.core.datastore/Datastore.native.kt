package com.jerryokafor.core.datastore

import com.jerryokafor.core.datastore.store.UserDataStore
import com.jerryokafor.core.datastore.store.UserSettingsStore
import com.jerryokafor.core.datastore.store.userSettingsStoreFileName
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun createUserDataStore(json: Json): UserDataStore = UserDataStore(json = json) {
    val documentDirectory: NSURL? =
        NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
    requireNotNull(documentDirectory).path + "/$dataStoreFileName"
}

@OptIn(ExperimentalForeignApi::class)
fun createUserSettingsStore(json: Json): UserSettingsStore = UserSettingsStore(json = json) {
    val documentDirectory: NSURL? =
        NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
    requireNotNull(documentDirectory).path + "/$userSettingsStoreFileName"
}
