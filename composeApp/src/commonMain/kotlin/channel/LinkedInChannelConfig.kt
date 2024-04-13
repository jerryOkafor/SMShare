package channel

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import model.TokenResponse
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_linkedin
import util.urlEncode

@OptIn(ExperimentalResourceApi::class, ExperimentalSerializationApi::class)
class LinkedInChannelConfig(
    override val name: String = "LinkedIn",
    override val description: String = "Profile or Page",
    override val icon: DrawableResource = Res.drawable.ic_linkedin
) : ChannelConfig {
    private val domain: String = "https://www.linkedin.com/oauth/v2/authorization"
    private val scope: List<String> = listOf("profile", "email", "w_member_social")
    private val clientId: String = ""
    private val clientSecret = ""


    override fun createLoginUrl(redirectUrl: String, challenge: String): String = domain +
            "?response_type=code&" +
            "client_id=$clientId&" +
            "redirect_uri=$redirectUrl&" +
            "state=$challenge&" +
            "scope=${urlEncode(scope.joinToString(" "))}"

    override suspend fun getToken(code: String, redirectUrl: String): TokenResponse {
        val client = HttpClient {
            install(ContentNegotiation) {
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    namingStrategy = JsonNamingStrategy.SnakeCase
                }
                json(json)

            }
        }

        return client.post("https://www.linkedin.com/oauth/v2/accessToken") {
            header("content-type", "application/x-www-form-urlencoded")
            setBody(
                "grant_type=authorization_code&" +
                        "client_id=$clientId&" +
                        "client_secret=$clientSecret&" +
                        "&code=$code&" +
                        "redirect_uri=$redirectUrl"
            )
        }.let {
            println(it.bodyAsText())
            it.body<TokenResponse>()
        }
    }
}
