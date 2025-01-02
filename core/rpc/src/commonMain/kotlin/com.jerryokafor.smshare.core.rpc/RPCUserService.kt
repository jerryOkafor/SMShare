package com.jerryokafor.smshare.core.rpc

import com.jerryokafor.smshare.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.RPC
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val address: String,
    val lastName: String,
)

interface RPCUserService : RPC {
    suspend fun hello(
        user: String,
        userData: UserData,
    ): String

    suspend fun subscribeToNews(): Flow<String>

    suspend fun login(
        userName: String,
        password: String,
    ): User
}
