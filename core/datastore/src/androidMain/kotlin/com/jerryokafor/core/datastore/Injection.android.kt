package com.jerryokafor.core.datastore

import android.content.Context
import com.jerryokafor.core.datastore.store.UserDataStore
import com.jerryokafor.core.datastore.store.UserSettingsStore
import org.koin.dsl.module

fun androidDataStoreModule(context: Context) = module {
    single<UserDataStore> { createUserDataStore(context = context, json = get()) }
    single<UserSettingsStore> { createUserSettingsStore(context = context, json = get()) }
}
