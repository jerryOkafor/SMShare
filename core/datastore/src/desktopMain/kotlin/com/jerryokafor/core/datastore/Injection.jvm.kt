package com.jerryokafor.core.datastore

import com.jerryokafor.core.datastore.store.UserDataStore
import com.jerryokafor.core.datastore.store.UserSettingsStore
import org.koin.dsl.module

fun jvmDataStoreModule() = module {
    single<UserDataStore> { createUserDatastore(json = get()) }
    single<UserSettingsStore> { createUserSettingsStore(json = get()) }
}
