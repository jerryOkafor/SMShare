package com.jerryokafor.smshare

import com.jerryokafor.smshare.core.model.User
import com.jerryokafor.smshare.core.rpc.UserData
import com.jerryokafor.smshare.core.rpc.UserService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.CoroutineContext

class RemoteUserService(override val coroutineContext: CoroutineContext) : UserService {
    override suspend fun hello(
        user: String,
        userData: UserData,
    ): String {
        return "Nice to meet you $user, how is it in ${userData.address}?"
    }

    override suspend fun subscribeToNews(): Flow<String> {
        return flow {
            repeat(10) {
                delay(300)
                emit("Article number $it")
            }
        }
    }

    override suspend fun login(
        userName: String,
        password: String,
    ): User {
        return User(
            email = "jerryhokafor@gmail.com",
            password = "12345678",
            firstName = "Jerry",
            lastName = "Okafor",
            phoneNumber = "+17809043389",
            token = "andsnldnlsnldnlsndnsndlnslndlnsl",
        )
    }
}
