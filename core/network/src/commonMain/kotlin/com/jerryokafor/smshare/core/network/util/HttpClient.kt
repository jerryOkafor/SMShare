package com.jerryokafor.smshare.core.network.util

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient
