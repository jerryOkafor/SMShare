package com.jerryokafor.smshare.core.domain

import com.jerryokafor.smshare.core.model.User
import kotlinx.coroutines.delay

interface UserService {
    suspend fun login(
        email: String,
        password: String,
    ): User

    suspend fun createAccount(
        email: String,
        password: String,
    ): User
}

class DefaultUserService : UserService {
    override suspend fun login(
        email: String,
        password: String,
    ): User {
        delay(200)
        return User(
            email = "Quincy",
            password = "Kennith",
            firstName = null,
            lastName = null,
            profilePictureUrl = null,
            phoneNumber = null,
            token = null,
        )
    }

    override suspend fun createAccount(
        email: String,
        password: String,
    ): User {
        delay(200)
        return User(
            email = "Quincy",
            password = "Kennith",
            firstName = null,
            lastName = null,
            profilePictureUrl = null,
            phoneNumber = null,
            token = null,
        )
    }
}
