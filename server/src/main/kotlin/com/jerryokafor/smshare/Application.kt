package com.jerryokafor.smshare

import com.apurebase.kgraphql.GraphQL
import com.auth0.jwk.JwkProviderBuilder
import com.jerryokafor.smshare.data.configureDatabase
import com.jerryokafor.smshare.graphql.smsShareScheme
import com.jerryokafor.smshare.injection.appModule
import com.jerryokafor.smshare.model.User
import com.jerryokafor.smshare.repository.DefaultUserRepository
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.http.content.staticFiles
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
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
//    install(RPC)
    installCORS()

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    val jwkProvider = JwkProviderBuilder(issuer)
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

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

    //Init DB
    configureDatabase()

    val userService by inject<DefaultUserRepository>()

    install(GraphQL) {
        useDefaultPrettyPrinter = true
        playground = true

        wrap {
            authenticate(
                configurations = arrayOf("jwt-auth"),
                optional = true,
                build = it
            )
        }

        context { call ->
            call.authentication.principal<User>()?.let { +it }
        }

        schema {
            smsShareScheme(
                environment = environment,
                userRepository = userService,
                jwkProvider = jwkProvider
            )
        }
    }

    routing {
//        rpc("/api") {
//            rpcConfig {
//                serialization {
//                    json()
//                }
//            }
//            registerService<RPCUserService> { ctx ->
//                RemoteRPCRPCUserService(ctx)
//            }
//        }

        staticFiles(remotePath = ".well-known", dir = File("certs"), index = "jwks.json") {}

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

        val allowedHosts = this@installCORS.environment.config.property("security.cors").getList()
        allowedHosts.forEach { host ->
            allowHost(host, listOf("http", "https", "ws", "wss"))
        }
    }
}
