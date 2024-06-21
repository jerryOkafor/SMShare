package screens.createAccount

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent

class CreateAccountViewModel :
    ViewModel(),
    KoinComponent {
    fun createAccount(
        email: String,
        password: String,
    ) {
    }
}
