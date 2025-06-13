package com.jerryokafor.smshare

import com.auth0.jwk.JwkProvider
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ContactDirective
import com.expediagroup.graphql.server.Schema
import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.defaultGraphQLStatusPages
import com.expediagroup.graphql.server.ktor.graphQLGetRoute
import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import com.expediagroup.graphql.server.ktor.graphQLSDLRoute
import com.expediagroup.graphql.server.ktor.graphiQLRoute
import com.jerryokafor.smshare.graphql.CreateUserMutationService
import com.jerryokafor.smshare.graphql.CustomGraphQLContextFactory
import com.jerryokafor.smshare.graphql.HelloWorldQuery
import com.jerryokafor.smshare.graphql.LoginMutationService
import com.jerryokafor.smshare.graphql.UpdateGreetingMutation
import com.jerryokafor.smshare.repository.DefaultUserRepository
import com.jerryokafor.smshare.util.AuthUtil
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

@ContactDirective(
    name = "SMS Share GraphQL Schema",
    url = "https://myteam.slack.com/archives/teams-chat-room-url",
    description = "send urgent issues to [#oncall](https://yourteam.slack.com/archives/oncall)."
)
@GraphQLDescription("SMS Share GraphQL Schema Description")
class SMSShareSchema : Schema


fun Application.installGraphQLModule(jwkProvider: JwkProvider) {
    val userRepository by inject<DefaultUserRepository>()
    val authUtil = AuthUtil(
        environment = environment,
        userRepository = userRepository,
        jwkProvider = jwkProvider
    )

    install(GraphQL) {
        schema {
            packages = listOf("com.jerryokafor.smshare")

            queries = listOf(
                HelloWorldQuery()
            )

            mutations = listOf(
                UpdateGreetingMutation(),
                LoginMutationService(userRepository, authUtil),
                CreateUserMutationService(userRepository, authUtil)
            )

            schemaObject = SMSShareSchema()

            server {
                contextFactory = CustomGraphQLContextFactory()
            }
        }
    }
    routing {
        graphQLGetRoute()
        graphQLPostRoute()
        graphQLSDLRoute()
        graphiQLRoute()

    }
    install(StatusPages) {
        defaultGraphQLStatusPages()
    }
}
