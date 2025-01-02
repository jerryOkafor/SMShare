package com.jerryokafor.smshare.core.common.injection

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

object KoinNamed {
    const val defaultDispatcher = "defaultDispatcher"
    const val ioDispatcher = "iODispatcher"
    const val mainDispatcher = "mainDispatcher"

    const val appScope = "appScope"
}


val dispatcherModule = module {
    factory<CoroutineDispatcher>(named(KoinNamed.defaultDispatcher)) { Dispatchers.Default }
    factory<CoroutineDispatcher>(named(KoinNamed.ioDispatcher)) { Dispatchers.IO }
    factory<CoroutineDispatcher>(named(KoinNamed.mainDispatcher)) { Dispatchers.Main }

    /*AppScope*/
    single<CoroutineScope>(named(KoinNamed.appScope)) {
        CoroutineScope(
            SupervisorJob() + get<CoroutineDispatcher>(
                named(KoinNamed.defaultDispatcher)
            )
        )
    }
}
