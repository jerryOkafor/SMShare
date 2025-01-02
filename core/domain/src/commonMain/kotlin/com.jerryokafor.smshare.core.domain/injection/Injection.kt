package com.jerryokafor.smshare.core.domain.injection

import com.jerryokafor.smshare.core.common.injection.KoinNamed
import com.jerryokafor.smshare.core.domain.DefaultUserRepository
import com.jerryokafor.smshare.core.domain.UserRepository
import dataStoreModule
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun domainModule() = module {
    includes(dataStoreModule())
    single<UserRepository> {
        DefaultUserRepository(
            apolloClient = get(),
            userDataStore = get(),
            ioDispatcher = get(named(KoinNamed.defaultDispatcher))
        )
    }
}
