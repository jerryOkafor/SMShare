package com.jerryokafor.core.datastore

import java.io.File

fun createUserDatastore(): UserDataStore = UserDataStore {
    File(System.getProperty("java.io.tmpdir"), dataStoreFileName).absolutePath
}
