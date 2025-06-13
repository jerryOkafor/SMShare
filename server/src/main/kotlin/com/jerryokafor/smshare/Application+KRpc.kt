@file:Suppress("ktlint:standard:filename")

package com.jerryokafor.smshare

import com.jerryokafor.smshare.core.rpc.RPCUserService
import com.jerryokafor.smshare.service.RemoteRPCRPCUserService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import kotlinx.rpc.krpc.ktor.server.Krpc
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json

fun Application.installKRPC() {
    // Install KRPC
    install(Krpc)

    routing {
        rpc("/api") {
            rpcConfig {
                serialization {
                    json()
                }
            }
            registerService<RPCUserService> { ctx ->
                RemoteRPCRPCUserService(ctx)
            }
        }
    }
}
