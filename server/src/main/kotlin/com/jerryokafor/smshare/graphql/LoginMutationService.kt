package com.jerryokafor.smshare.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.jerryokafor.smshare.model.User
import com.jerryokafor.smshare.repository.DefaultUserRepository
import com.jerryokafor.smshare.util.AuthUtil

data class AuthPayload(
    val accessToken: String? = null,
    val user: User? = null,
)

@Suppress("UnusedPrivateProperty")
class LoginMutationService(
    userService: DefaultUserRepository,
    private val authUtil: AuthUtil,
) : Mutation {
    @GraphQLDescription("Create a new access token for an existing user")
    @Suppress("UnusedParameter")
    suspend fun login(
        userName: String,
        password: String,
    ): AuthPayload {
        val token = authUtil.createAccessToken(userName)
        val user = User(
            email = "fake@site.com",
            firstName = "Someone",
            lastName = "You Don't know",
        )
        return AuthPayload(token, user)
    }
}
