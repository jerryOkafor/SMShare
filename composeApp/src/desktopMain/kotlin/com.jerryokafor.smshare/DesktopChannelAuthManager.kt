/*
 * MIT License
 *
 * Copyright (c) 2024 SM Share Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jerryokafor.smshare

import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelConfig
import com.jerryokafor.smshare.channel.oAuth2ResponseBuilder
import io.ktor.http.HttpStatusCode
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
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

class DesktopChannelAuthManager(
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) : ChannelAuthManager {
    override lateinit var currentChallenge: String

    private var coroutineScope: CoroutineScope = coroutineScope
        private set

    private val callbackJob = MutableStateFlow<Job?>(null)
    override var currentChannelConfig: ChannelConfig? = null

    override fun getState(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return java.util.Base64
            .getUrlEncoder()
            .withoutPadding()
            .encodeToString(code)
    }

    override suspend fun authenticateUser(channelConfig: ChannelConfig) {
        currentChannelConfig = channelConfig
        val job = coroutineScope.launch {
            try {
                val challenge = getChallenge()
                val redirectUrl = getRedirectUrl()
                val url = channelConfig.createLoginUrl(
                    redirectUrl = redirectUrl,
                    state = getState(),
                    challenge = challenge,
                )

                withContext(Dispatchers.IO) {
                    Desktop.getDesktop().browse(URI(url))
                }

                waitForCallback()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        callbackJob.value = job
        job.invokeOnCompletion { callbackJob.value = null }
    }

    private suspend fun waitForCallback(): String {
        var server: EmbeddedServer<*, *>? = null

        val code = suspendCancellableCoroutine { continuation ->
            server = embeddedServer(factory = Netty, port = AUTH_SERVER_PORT) {
                routing {
                    get("/callback") {
                        val code =
                            call.parameters["code"]
                                ?: throw RuntimeException("Received a response with no code")
                        val state =
                            call.parameters["state"]
                                ?: throw RuntimeException("Received a response with no code")
                        println("OAuth finished: Code: $code,\nState: $state")
                        call.respondHtml(HttpStatusCode.OK, oAuth2ResponseBuilder())

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

    override suspend fun getChallenge(): String = if (::currentChallenge.isInitialized) {
        currentChallenge
    } else {
        val bytes: ByteArray = createVerifier().toByteArray(Charsets.US_ASCII)
        val md = MessageDigest.getInstance("SHA-256")
        md.update(bytes, 0, bytes.size)
        val digest = md.digest()
        val challenge = Base64.encodeBase64URLSafeString(digest)
        currentChallenge = challenge
        challenge
    }

    override suspend fun getRedirectUrl(): String = "http://localhost:${AUTH_SERVER_PORT}/callback"

    private fun createVerifier(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return java.util.Base64
            .getUrlEncoder()
            .withoutPadding()
            .encodeToString(code)
    }

    companion object {
        private const val AUTH_SERVER_PORT = 5789
    }
}
