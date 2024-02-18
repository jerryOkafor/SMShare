package injection

import AuthManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

fun iOSModules() = module {
    single { AuthManager() }
}

// Needed for iOS
fun initKoin() = initKoin {
    modules(iOSModules())
}

class IOSInjectionHelper : KoinComponent {
    val authManager: AuthManager by inject()
}