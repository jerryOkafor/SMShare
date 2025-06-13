package com.jerryokafor.smshare.core.domain

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import com.jerryokafor.core.datastore.store.UserDataStore
import com.jerryokafor.smshare.graphql.CreateUserMutation
import com.jerryokafor.smshare.graphql.LoginUserMutation
import com.jerryokafor.smshare.graphql.type.CreateUserInput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface UserRepository {

    fun login(userName: String, password: String): Flow<Outcome<Unit>>

    fun createAccount(email: String, password: String): Flow<Outcome<Unit>>
}

class DefaultUserRepository(
    private val userDataStore: UserDataStore,
    private val apolloClient: ApolloClient,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {
    override fun login(userName: String, password: String) = flow {
        try {
            val response = apolloClient.mutation(
                LoginUserMutation(userName = userName, password = password)
            ).execute().dataAssertNoErrors.login

            userDataStore.loginUser(response.accessToken!!)
            emit(Success(Unit))
        } catch (e: ApolloException) {
            e.printStackTrace()
            emit(
                Failure(
                    errorResponse = e.message ?: "Error login in user, please try again",
                    throwable = e.cause
                )
            )
        }
    }.flowOn(ioDispatcher)

    override fun createAccount(email: String, password: String): Flow<Outcome<Unit>> = flow {
        try {
            val response = apolloClient.mutation(
                CreateUserMutation(input = CreateUserInput(email, firstName = "", lastName = ""))
            ).execute().dataAssertNoErrors.createUser

            userDataStore.loginUser(response.accessToken!!)
            emit(Success(Unit))
        } catch (e: ApolloException) {
            emit(
                Failure(
                    errorResponse = e.message ?: "Error creating user, please try again",
                    throwable = e.cause
                )
            )
        }
    }.flowOn(ioDispatcher)
}
