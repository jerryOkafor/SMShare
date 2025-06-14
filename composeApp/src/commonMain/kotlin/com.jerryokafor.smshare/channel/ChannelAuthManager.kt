@file:Suppress("InvalidPackageDeclaration")

package com.jerryokafor.smshare.channel

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
 * Interface to manage [ChannelConfig]
 * */
interface ChannelAuthManager {
    var currentChallenge: String
    var currentChannelConfig: ChannelConfig?

    fun getState(): String

    suspend fun authenticateUser(channelConfig: ChannelConfig)

    suspend fun getChallenge(): String

    suspend fun getRedirectUrl(): String
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
