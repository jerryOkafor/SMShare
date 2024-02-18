import channel.ChannelConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.security.MessageDigest
import java.security.SecureRandom
import org.apache.commons.codec.binary.Base64
import java.awt.Desktop
import java.net.URI
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume


actual class AuthManager private actual constructor() {

    lateinit var coroutineScope: CoroutineScope
        private set

    constructor(coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) : this() {
        this.coroutineScope = coroutineScope
    }

    private val callbackJob = MutableStateFlow<Job?>(null)

    actual fun authenticateUser(channelConfig: ChannelConfig) {

        val job = coroutineScope.launch {
            try {
                val verifier = createVerifier()
                val challenge = createChallenge(verifier)
                val url = channelConfig.createLoginUrl("http://localhost:5789/callback", challenge)

                println("Launching: $url")

                withContext(Dispatchers.IO) {
                    Desktop.getDesktop().browse(URI(url))
                }

                val code = waitForCallback()

                println("Code: $code")

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        callbackJob.value = job
        job.invokeOnCompletion { callbackJob.value = null }

    }


    private suspend fun waitForCallback(): String {
        var server: NettyApplicationEngine? = null

        val code = suspendCancellableCoroutine { continuation ->
            server = embeddedServer(Netty, port = 5789) {
                routing {
                    get("/callback") {
                        val code = call.parameters["code"]
                            ?: throw RuntimeException("Received a response with no code")
                        val state =
                            call.parameters["state"]
                                ?: throw RuntimeException("Received a response with no code")
                        println("OAuth finished: Code: $code,\nState: $state")
                        call.respondText("User authenticated")

                        continuation.resume(code)
                    }
                }
            }.start(wait = false)
        }

        coroutineScope.launch {
            server?.stop(1, 5, TimeUnit.SECONDS)
        }

        return code
    }

    private fun createVerifier(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(code)
    }

    private fun createChallenge(verifier: String): String {
        val bytes: ByteArray = verifier.toByteArray(Charsets.US_ASCII)
        val md = MessageDigest.getInstance("SHA-256")
        md.update(bytes, 0, bytes.size)
        val digest = md.digest()
        return Base64.encodeBase64URLSafeString(digest)
    }

    actual var currentChannel: ChannelConfig?
        get() = TODO("Not yet implemented")
        set(value) {}
}

