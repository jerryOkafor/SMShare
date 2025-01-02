package com.jerryokafor.core.datastore.serializer

import androidx.datastore.core.okio.OkioSerializer
import com.jerryokafor.core.datastore.model.UserSettings
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use

internal class UserSettingsSerializer(private val json: Json) : OkioSerializer<UserSettings> {
    override val defaultValue: UserSettings = UserSettings()

    override suspend fun readFrom(source: BufferedSource): UserSettings =
        json.decodeFromString<UserSettings>(source.readUtf8())

    override suspend fun writeTo(
        t: UserSettings,
        sink: BufferedSink,
    ) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserSettings.serializer(), t))
        }
    }
}
