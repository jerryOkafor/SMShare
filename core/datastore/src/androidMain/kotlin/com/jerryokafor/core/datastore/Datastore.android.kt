package com.jerryokafor.core.datastore

import android.content.Context

fun createUserDataStore(context: Context): UserDataStore = UserDataStore {
    context.filesDir.resolve(dataStoreFileName).absolutePath
}
