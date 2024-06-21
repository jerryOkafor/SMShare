package com.jerryokafor.core.datastore

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

@Suppress("TopLevelPropertyNaming", "ktlint:standard:property-naming")
internal const val dataStoreFileName = "sms.share_pb"

val json = Json { ignoreUnknownKeys = true }

class UserDataStore(
    private val produceFilePath: () -> String,
) {
    private val db = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = UserPreferencesSerializer,
            producePath = {
                produceFilePath().toPath()
            },
        ),
    )

    val user: Flow<UserData>
        get() = db.data

    suspend fun loginUser() {
        db.updateData { it.copy(isLoggedIn = true) }
    }

    suspend fun logoOutUser() {
        db.updateData { it.copy(isLoggedIn = false) }
    }
}
