package com.jerryokafor.core.datastore

import org.koin.dsl.module

fun nativeUserDatastoreModule() = module {
        single<UserDataStore> { createDataStore() }
    }
