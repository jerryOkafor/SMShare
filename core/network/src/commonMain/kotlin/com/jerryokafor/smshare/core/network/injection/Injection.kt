package com.jerryokafor.smshare.core.network.injection

import com.apollographql.apollo.ApolloClient
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
        ApolloClient.Builder()
            .serverUrl("http://192.168.68.105:8080/graphql")
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
