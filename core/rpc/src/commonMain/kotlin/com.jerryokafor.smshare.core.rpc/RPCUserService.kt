package com.jerryokafor.smshare.core.rpc

import com.jerryokafor.smshare.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val address: String,
    val lastName: String,
)

@Rpc
interface RPCUserService : RemoteService {
    suspend fun hello(message: String): String

    fun subscribeToNews(): Flow<String>

    suspend fun login(userName: String, password: String): User
}
