package com.jerryokafor.smshare.core.domain.injection

import com.jerryokafor.smshare.core.domain.DefaultUserService
import com.jerryokafor.smshare.core.domain.UserService
import org.koin.dsl.module

fun domainModule() = module {
    single<UserService> { DefaultUserService() }
}
