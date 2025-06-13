package com.jerryokafor.smshare.repository

import com.jerryokafor.smshare.data.DatabaseFactoryImpl
import com.jerryokafor.smshare.data.SchemaDefinition
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.Database
import org.junit.Before
import kotlin.test.Test

open class BaseDbTest {
    lateinit var database: Database

    @Before
    fun initDb() {
        database = DatabaseFactoryImpl(isTest = true).create()
        // Init DB
        SchemaDefinition.createSchema()
    }
}

class DefaultUserRepositoryTest : BaseDbTest() {
    private lateinit var userRepo: DefaultUserRepository

    @Before
    fun setUp() {
        userRepo = DefaultUserRepository(database)
    }

    @Test
    fun `userRepo createUser`() = runTest {
        @Suppress("UnusedPrivateProperty")
        val user = userRepo.createUer("email@test.com", "P@ssw0rd")
    }
}
