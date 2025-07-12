@file:Suppress("InvalidPackageDeclaration")

package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.domain.ChannelConfig
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.style
import kotlinx.html.title
import kotlinx.html.unsafe

/**
 * Interface to manage [com.jerryokafor.smshare.core.domain.ChannelConfig]
 *
 * Flow: Your application generates the Code Verifier, creates the Code Challenge,
 * and sends the Code Challenge to the authorization server during the authorization
 * request. The authorization server stores the Code Challenge. When your application
 * exchanges the authorization code for an access token, it sends the original Code
 * Verifier. The authorization server verifies that the Code Verifier matches the
 * stored Code Challenge.
 * */
interface ChannelAuthManager {
    /**
     * A randomly generated, cryptographically strong string by your application.
     * */
    var codeVerifier: String
    
    var channelConfig: ChannelConfig?

    fun getState(): String

    suspend fun authenticateUser(channelConfig: ChannelConfig)

    /**
     * A transformed (usually hashed) version of the Code Verifier.
     * */
    suspend fun getCodeChallenge(): String

    suspend fun getRedirectUrl(): String
}

object ExternalUriHandler {
    // Storage for when a URI arrives before the listener is set up
    private var cached: String? = null

    var listener: ((uri: String) -> Unit)? = null
        set(value) {
            field = value
            if (value != null) {
                // When a listener is set and `cached` is not empty,
                // immediately invoke the listener with the cached URI
                cached?.let { value.invoke(it) }
                cached = null
            }
        }

    // When a new URI arrives, cache it.
    // If the listener is already set, invoke it and clear the cache immediately.
    fun onNewUri(uri: String) {
        cached = uri
        listener?.let {
            it.invoke(uri)
            cached = null
        }
    }
}

fun oAuth2ResponseBuilder(): HTML.() -> Unit {
    val css = """
        body {
          font-family: Arial, sans-serif;
          background: #f4f6f8;
          display: flex;
          align-items: center;
          justify-content: center;
          height: 100vh;
          margin: 0;
        }
        .container {
          background: white;
          padding: 2rem 3rem;
          border-radius: 12px;
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
          text-align: center;
        }
        .icon {
          font-size: 48px;
          color: #4caf50;
        }
        h1 {
          margin-top: 0.5rem;
          color: #333;
        }
        p {
          color: #555;
        }
    """.trimIndent()

    return {
        head {
            meta(charset = "UTF-8")
            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1.0"
            }
            title("Authorization Successful")
            style {
                unsafe { raw(css) }
            }
        }
        body {
            div("container") {
                div("icon") { +"✅" }
                h1 { +"Authorization Successful" }
                p { +"Your application has been authorized successfully." }
                p { +"You can now close this window." }
            }
        }
    }
}
