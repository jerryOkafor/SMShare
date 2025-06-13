@file:Suppress("InvalidPackageDeclaration")

package com.jerryokafor.core.datastore.store

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.jerryokafor.core.datastore.model.UserData
import com.jerryokafor.core.datastore.serializer.UserPreferencesSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

@Suppress("TopLevelPropertyNaming", "ktlint:standard:property-naming")
internal const val userDataStoreFileName = "sms.share_pb"

class UserDataStore(
    private val json: Json,
    private val produceFilePath: () -> String,
) {
    private val db = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = UserPreferencesSerializer(json),
            producePath = {
                produceFilePath().toPath()
            },
        ),
    )

    val user: Flow<UserData>
        get() = db.data

    suspend fun loginUser(accessToken: String) {
        db.updateData { it.copy(isLoggedIn = true, token = accessToken) }
    }

    suspend fun logoOutUser() {
        db.updateData { UserData() }
    }
}
