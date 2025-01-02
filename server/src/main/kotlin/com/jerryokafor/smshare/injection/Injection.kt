package com.jerryokafor.smshare.injection

import com.jerryokafor.smshare.data.DatabaseFactory
import com.jerryokafor.smshare.data.DatabaseFactoryImpl
import com.jerryokafor.smshare.repository.DefaultUserRepository
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val appModule = module {
    single<DatabaseFactory> { DatabaseFactoryImpl() }
    single<Database> { DatabaseFactoryImpl().create() }
    single { DefaultUserRepository(database = get()) }
}
