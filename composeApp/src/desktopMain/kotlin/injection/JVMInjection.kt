package injection

import AuthManager
import org.koin.dsl.module

val desktopModule = module {
    single { AuthManager() }
}