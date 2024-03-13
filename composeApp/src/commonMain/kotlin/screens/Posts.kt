package screens

import AuthManager
import Greeting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import channel.ChannelConfig
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import util.oauthChallenge
import util.oauthChallengeVerify


class HomeScreenModel : ScreenModel

class Posts : Screen {
    @Composable
    override fun Content() {
        val model = remember { HomeScreenModel() }
        val greeting = koinInject<Greeting>()
        val authManager = koinInject<AuthManager>()
        val supportedChannels = koinInject<List<ChannelConfig>>()

        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                scope.launch {
                    val verify = oauthChallengeVerify()
                    println("verify: $verify")
                    println("challenge: ${oauthChallenge(verify)}")
                }
                authManager.authenticateUser(supportedChannels.first())
            }) {
                Text("Launch Auth Flow")
            }
        }
    }

}
