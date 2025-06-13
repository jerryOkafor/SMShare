package com.jerryokafor.smshare.repository

import com.jerryokafor.smshare.data.toPasswordHash
import com.jerryokafor.smshare.model.User
import com.jerryokafor.smshare.model.UserDao
import com.jerryokafor.smshare.model.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.orWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQueryOnIO(
    db: Database? = null,
    block: suspend () -> T,
): T = newSuspendedTransaction(context = Dispatchers.IO, db = db) { block() }

interface UserRepository {
    suspend fun createUer(
        email: String,
        password: String,
    )

    suspend fun updateUser(
        userName: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null,
        password: String? = null,
    )

    suspend fun getUserByEmailOrUserName(userName: String): User?

    suspend fun getAllUsers(): List<User>
}

class DefaultUserRepository(
    private val database: Database,
) : UserRepository {
    override suspend fun createUer(
        email: String,
        password: String,
    ) {
        dbQueryOnIO(db = database) {
            Users.insert {
                it[Users.email] = email
                it[Users.password] = password.toPasswordHash()
                it[createdAt] = System.currentTimeMillis()
            }
        }
    }

    override suspend fun updateUser(
        userName: String?,
        firstName: String?,
        lastName: String?,
        email: String?,
        password: String?,
    ) {
        val predicates: Op<Boolean> = when {
            email != null -> Op.build { Users.email eq email }
            userName != null -> Op.build { Users.userName eq userName }
            else -> Op.nullOp()
        }

        dbQueryOnIO(database) {
            val existingUser = UserDao.find { predicates }.firstOrNull()

            if (userName != null) {
                existingUser?.userName = userName
            }

            if (firstName != null) {
                existingUser?.firstName = firstName
            }

            if (lastName != null) {
                existingUser?.lastName = lastName
            }

            if (email != null) {
                existingUser?.email = email
            }

            if (password != null) {
                existingUser?.password = password
            }
        }
    }

    override suspend fun getUserByEmailOrUserName(userName: String): User? =
        dbQueryOnIO(db = database) {
            Users
                .select(Users.columns)
                .where {
                    Users.userName eq userName
                }.orWhere {
                    Users.email eq userName
                }.limit(1)
                .singleOrNull()
                ?.let { toUsers(it) }
        }

    override suspend fun getAllUsers(): List<User> = dbQueryOnIO(db = database) {
        Users.selectAll().map { toUsers(it) }
    }

    private fun toUsers(row: ResultRow): User = User(
        id = row[Users.id].value,
        firstName = row[Users.firstName],
        lastName = row[Users.lastName],
        email = row[Users.email],
        userName = row[Users.userName],
//            createdAt = row[Users.createdAt],
        password = row[Users.password],
    )
}
