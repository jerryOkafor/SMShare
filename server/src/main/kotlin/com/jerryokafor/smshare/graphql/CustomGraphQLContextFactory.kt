package com.jerryokafor.smshare.graphql

import com.expediagroup.graphql.generator.extensions.plus
import com.expediagroup.graphql.server.ktor.DefaultKtorGraphQLContextFactory
import com.jerryokafor.smshare.model.User
import graphql.GraphQLContext
import io.ktor.server.request.ApplicationRequest

/**
 * Provides a Ktor specific implementation of GraphQLContextFactory and the context.
 * */
class CustomGraphQLContextFactory : DefaultKtorGraphQLContextFactory() {
    override suspend fun generateContext(request: ApplicationRequest): GraphQLContext =
        super.generateContext(request).plus(
            mutableMapOf<Any, Any>(
                "user" to User(
                    email = "fake@site.com",
                    firstName = "Someone",
                    lastName = "You Don't know",
                ),
            ).also { map ->
                request.headers["my-custom-header"]?.let { customHeader ->
                    map["customHeader"] = customHeader
                }
            },
        )
}
