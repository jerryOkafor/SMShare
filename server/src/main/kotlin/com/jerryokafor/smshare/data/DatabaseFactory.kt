package com.jerryokafor.smshare.data


import com.jerryokafor.smshare.model.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt

fun String.toPasswordHash(): String = BCrypt.hashpw(this, BCrypt.gensalt())

interface DatabaseFactory {
    fun create(): Database
}

class DatabaseFactoryImpl(isTest: Boolean = true) : DatabaseFactory {

    @Volatile
    private var INSTANCE: Database? = null

    private fun h2Hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:mem:test"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }


    override fun create(): Database {
        synchronized(this) {
            var instance = INSTANCE
            return instance ?: Database.connect(h2Hikari()).also { INSTANCE = it }
        }
    }
}

object SchemaDefinition {
    fun createSchema() {
        transaction {
            SchemaUtils.create(Users)

            Users.insert {
                it[firstName] = "Jen Keny"
                it[userName] = "username1"
                it[email] = "jenkeny@yopmail.com"
                it[password] = "P@ssw0rd1".toPasswordHash()
                it[createdAt] = System.currentTimeMillis()
            }
            Users.insert {
                it[firstName] = "Ramsey Noah"
                it[userName] = "username2"
                it[email] = "ramsey@yopmail.com"
                it[password] = "P@ssw0rd2".toPasswordHash()
                it[createdAt] = System.currentTimeMillis()
            }
        }
    }
}

fun Application.configureDatabase() {
    val databaseFactory by inject<DatabaseFactory>()
    databaseFactory.create()

    //Init DB
    SchemaDefinition.createSchema()
}

//object DatabaseFactory {
//
//    fun init(environment: ApplicationEnvironment) {
//        val dbUrl = ""
//        val dbUser = ""
//        val dbPassword = ""
//
//        val database = Database.connect(h2Hikari())
//
//        transaction(database) {
//            create(Users)

//        }
//    }
//

//
//    private fun pgHikari(dbUrl: String, dbUser: String, dbPassword: String): HikariDataSource {
//        val config = HikariConfig().apply {
//            driverClassName = "org.postgres.Driver"
//            jdbcUrl = dbUrl
//            username = dbUser
//            password = dbPassword
//            maximumPoolSize = 3
//            isAutoCommit = false
//            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
//            validate()
//        }
//
//        return HikariDataSource(config)
//    }
//}
