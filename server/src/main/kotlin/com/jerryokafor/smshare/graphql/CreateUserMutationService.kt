package com.jerryokafor.smshare.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.jerryokafor.smshare.util.AuthUtil
import com.jerryokafor.smshare.repository.DefaultUserRepository

data class CreateUserInput(val email: String, val firstName: String, val lastName: String)

class CreateUserMutationService(
    userService: DefaultUserRepository,
    private val authUtil: AuthUtil
) : Mutation {

    @GraphQLDescription("Create a new user and return access token")
    suspend fun createUser(input: CreateUserInput): AuthPayload {

        return AuthPayload()
    }
}
