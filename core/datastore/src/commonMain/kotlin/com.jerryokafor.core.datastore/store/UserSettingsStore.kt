@file:Suppress(
    "TopLevelPropertyNaming",
    "ktlint:standard:property-naming",
    "InvalidPackageDeclaration",
)

package com.jerryokafor.core.datastore.store

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.jerryokafor.core.datastore.model.UserSettings
import com.jerryokafor.core.datastore.serializer.UserSettingsSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

internal const val userSettingsStoreFileName = "sms-settings.share_pb"

class UserSettingsStore(
    private val json: Json,
    private val produceFilePath: () -> String,
) {
    private val db = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = UserSettingsSerializer(json),
            producePath = {
                produceFilePath().toPath()
            },
        ),
    )

    val user: Flow<UserSettings>
        get() = db.data
}
