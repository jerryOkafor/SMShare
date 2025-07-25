package com.jerryokafor.smshare.core.domain.injection

import com.jerryokafor.smshare.core.common.injection.KoinNamed
import com.jerryokafor.smshare.core.domain.AccountRepository
import com.jerryokafor.smshare.core.domain.DefaultAccountRepository
import com.jerryokafor.smshare.core.domain.DefaultTagRepository
import com.jerryokafor.smshare.core.domain.DefaultUserRepository
import com.jerryokafor.smshare.core.domain.TagRepository
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
            ioDispatcher = get(named(KoinNamed.defaultDispatcher)),
        )
    }

    single<AccountRepository> {
        DefaultAccountRepository(
            accountDao = get(),
            apolloClient = get(),
            ioDispatcher = get(named(KoinNamed.defaultDispatcher))
        )
    }

    single<TagRepository> {
        DefaultTagRepository(
            tagDao = get(),
            apolloClient = get(),
            ioDispatcher = get(
                named(KoinNamed.defaultDispatcher)
            )
        )
    }
}
