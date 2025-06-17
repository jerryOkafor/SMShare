package com.jerryokafor.smshare.core.network.util

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.*
import io.ktor.client.engine.darwin.*


actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(Darwin) {
    config(this)
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}
