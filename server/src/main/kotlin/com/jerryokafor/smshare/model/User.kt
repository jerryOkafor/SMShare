@file:Suppress("MagicNumber")

package com.jerryokafor.smshare.model

import io.ktor.server.auth.Principal
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val firstName = varchar("firstName", 255).nullable()
    val lastName = varchar("lastName", 255).nullable()
    val userName = varchar("userName", 255).nullable()
    val email = varchar("email", 255)
    val createdAt = long("createdAt")
    val password = varchar("password", 255)
}

class UserDao(
    id: EntityID<Int>,
) : Entity<Int>(id) {
    companion object : EntityClass<Int, UserDao>(Users)

    var firstName by Users.firstName
    var lastName by Users.lastName
    var userName by Users.userName
    var email by Users.email
    var password by Users.password
    var createdAt by Users.createdAt
}

@Serializable
data class User(
    val id: Int = -1,
    val firstName: String? = "",
    val lastName: String? = "",
    val userName: String? = "",
    val email: String = "",
//    val createdAt: Long = 0L,
    val token: String? = null,
    @Transient
    val password: String? = null,
) : Principal
