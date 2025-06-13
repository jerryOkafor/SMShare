package com.jerryokafor.smshare.core.network.injection

import com.apollographql.apollo.ApolloClient
import com.jerryokafor.smshare.core.config.Config
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
fun commonNetworkModule() = module {
    single {
        ApolloClient
            .Builder()
            .serverUrl("http://${Config.IP_ADDRESS}:${Config.API_PORT}/graphql")
            .build()
    }
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                val json =
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        namingStrategy = JsonNamingStrategy.SnakeCase
                    }
                json(json)
            }
        }
    }
}
