package com.jerryokafor.smshare

import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelConfig
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.stop
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Base64
import java.awt.Desktop
import java.net.URI
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

class DesktopChannelAuthManager(coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) :
    ChannelAuthManager {
    private var coroutineScope: CoroutineScope = coroutineScope
        private set

    private val callbackJob = MutableStateFlow<Job?>(null)

    override suspend fun authenticateUser(channelConfig: ChannelConfig) {
        val job =
            coroutineScope.launch {
                try {
                    val challenge = getChallenge()
                    val redirectUrl = getRedirectUrl()
                    val url = channelConfig.createLoginUrl(redirectUrl, challenge)

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

        val code =
            suspendCancellableCoroutine { continuation ->
                server =
                    embeddedServer(Netty, port = 5789) {
                        routing {
                            get("/callback") {
                                val code =
                                    call.parameters["code"]
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

        // Wait for 5 seconds and timeout
        coroutineScope.launch {
            server?.stop(1, 5, TimeUnit.SECONDS)
        }

        return code
    }

    override suspend fun getChallenge(): String {
        val bytes: ByteArray = createVerifier().toByteArray(Charsets.US_ASCII)
        val md = MessageDigest.getInstance("SHA-256")
        md.update(bytes, 0, bytes.size)
        val digest = md.digest()
        return Base64.encodeBase64URLSafeString(digest)
    }

    override suspend fun getRedirectUrl(): String = "http://localhost:5789/callback"

    private fun createVerifier(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(code)
    }
}
