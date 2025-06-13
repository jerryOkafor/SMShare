package com.jerryokafor.core.datastore

import com.jerryokafor.core.datastore.store.UserSettingsStore
import org.koin.dsl.module

fun nativeUserDatastoreModule() = module {
    single<UserDataStore> { createUserDataStore(json = get()) }
    single<UserSettingsStore> { createUserSettingsStore(json = get()) }
}
