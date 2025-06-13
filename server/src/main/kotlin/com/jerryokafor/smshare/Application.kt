
package com.jerryokafor.smshare

import com.auth0.jwk.JwkProviderBuilder
import com.jerryokafor.smshare.core.rpc.RPCUserService
import com.jerryokafor.smshare.data.configureDatabase
import com.jerryokafor.smshare.injection.appModule
import com.jerryokafor.smshare.service.RemoteRPCRPCUserService
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticFiles
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.origin
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.rpc.krpc.ktor.server.Krpc
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import java.io.File
import java.util.concurrent.TimeUnit

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }

    // Install Cors
    installCORS()

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    val jwkProvider = JwkProviderBuilder(issuer)
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    //Init DB Connection
    configureDatabase()

    install(Authentication) {
        jwt("jwt-auth") {
            realm = myRealm

            verifier(jwkProvider, issuer) {
                acceptLeeway(3)
            }

            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

    installKRPC()

    installGraphQLModule(jwkProvider)

    intercept(ApplicationCallPipeline.Monitoring) {
        call.request.origin.apply {
            println("Request URL: $scheme://$localHost:$localPort$uri")
        }
    }

    routing {
        staticFiles(remotePath = ".well-known", dir = File("../server/certs"), index = "jwks.json")

        get("/") {
            call.respondText("Hello from SMS Share App Ktor Service (GraphQL + RPC + HTTP)")
        }
    }
}

fun Application.installCORS() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.Upgrade)
        allowNonSimpleContentTypes = true
        allowCredentials = true
        allowSameOrigin = true

        val allowedHosts = this@installCORS.environment.config.propertyOrNull("security.cors")?.getList()
        allowedHosts?.forEach { host ->
            allowHost(host, listOf("http", "https", "ws", "wss"))
        }
    }
}
