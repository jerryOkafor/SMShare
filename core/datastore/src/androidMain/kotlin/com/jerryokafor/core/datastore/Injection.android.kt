package com.jerryokafor.core.datastore

import android.content.Context
import org.koin.dsl.module

fun androidDataStoreModule(context: Context) = module {
    single<UserDataStore> { createUserDataStore(context) }
}
