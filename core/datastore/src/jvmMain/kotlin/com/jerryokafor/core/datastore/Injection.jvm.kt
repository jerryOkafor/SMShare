package com.jerryokafor.core.datastore

import org.koin.dsl.module

fun jvmDataStoreModule() =
    module {
        single<UserDataStore> { createUserDatastore() }
    }
