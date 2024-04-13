package injection

import Greeting
import Platform
import channel.ChannelConfig
import channel.FacebookChannelConfig
import channel.LinkedInChannelConfig
import channel.XChannelConfig
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import screens.addNewConnectionModel

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    return startKoin {
        appDeclaration()
        modules(commonModule(), addNewConnectionModel)
    }
}


@Suppress("OPT_IN_USAGE_FUTURE_ERROR")
fun commonModule() = module {
    single { Platform() }
    single { Greeting(get()) }

    //inject supported channel config
    single<List<ChannelConfig>> {
        listOf(
            LinkedInChannelConfig(),
            FacebookChannelConfig(),
            XChannelConfig()
        )
    }
}