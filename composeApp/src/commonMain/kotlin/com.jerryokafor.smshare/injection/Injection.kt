package com.jerryokafor.smshare.injection

import com.jerryokafor.core.database.injection.commonDatabaseModules
import com.jerryokafor.smshare.AppViewModel
import com.jerryokafor.smshare.channel.ChannelConfig
import com.jerryokafor.smshare.channel.FacebookChannelConfig
import com.jerryokafor.smshare.channel.LinkedInChannelConfig
import com.jerryokafor.smshare.channel.XChannelConfig
import com.jerryokafor.smshare.core.common.injection.dispatcherModule
import com.jerryokafor.smshare.core.domain.injection.domainModule
import com.jerryokafor.smshare.core.network.injection.commonNetworkModule
import com.jerryokafor.smshare.screens.compose.ComposeMessageViewModel
import com.jerryokafor.smshare.screens.auth.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import com.jerryokafor.smshare.screens.auth.createAccount.CreateAccountViewModel

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
    single<List<ChannelConfig>> {
        listOf(
            LinkedInChannelConfig(httpClient = get()),
            FacebookChannelConfig(),
            XChannelConfig(),
        )
    }

    viewModelOf(::AppViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::CreateAccountViewModel)
    viewModelOf(::ComposeMessageViewModel)
}

// suspend inline fun <reified T : RPC> HttpClient.bindRPC(): T {
//    return rpc {
//        url {
//            host = DEV_SERVER_HOST
//            port = 8080
//            encodedPath = "/api"
//        }
//
//        rpcConfig {
//            serialization {
//                json()
//            }
//        }
//    }.withService<T>()
// }
