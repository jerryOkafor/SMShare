package com.jerryokafor.smshare.injection

import com.jerryokafor.core.database.injection.commonDatabaseModules
import com.jerryokafor.smshare.AppViewModel
import com.jerryokafor.smshare.channel.ChannelConfigResource
import com.jerryokafor.smshare.core.domain.ChannelConfig
import com.jerryokafor.smshare.channel.FacebookChannelConfig
import com.jerryokafor.smshare.channel.LinkedInChannelConfig
import com.jerryokafor.smshare.channel.XChannelConfig
import com.jerryokafor.smshare.core.common.injection.dispatcherModule
import com.jerryokafor.smshare.core.domain.injection.domainModule
import com.jerryokafor.smshare.core.network.injection.commonNetworkModule
import com.jerryokafor.smshare.screens.accounts.AccountsViewModel
import com.jerryokafor.smshare.screens.auth.createAccount.CreateAccountViewModel
import com.jerryokafor.smshare.screens.auth.login.LoginViewModel
import com.jerryokafor.smshare.screens.compose.ComposeMessageViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication = startKoin {
    appDeclaration()
    modules(
        dispatcherModule,
        commonModule(),
        domainModule(),
        commonNetworkModule(),
        commonDatabaseModules(),
    )
}

fun commonModule() = module {
    single<List<ChannelConfigResource>> {
        listOf(
            LinkedInChannelConfig(httpClient = get()),
            FacebookChannelConfig(httpClient = get()),
            XChannelConfig(httpClient = get()),
        )
    }

    viewModelOf(::AppViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::CreateAccountViewModel)
    viewModelOf(::ComposeMessageViewModel)
    viewModelOf(::AccountsViewModel)
}
