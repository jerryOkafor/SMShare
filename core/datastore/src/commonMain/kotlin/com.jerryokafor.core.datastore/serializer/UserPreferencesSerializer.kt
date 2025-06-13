@file:Suppress("InvalidPackageDeclaration")

package com.jerryokafor.core.datastore.serializer

import androidx.datastore.core.okio.OkioSerializer
import com.jerryokafor.core.datastore.model.UserData
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use

internal class UserPreferencesSerializer(
    private val json: Json,
) : OkioSerializer<UserData> {
    override val defaultValue: UserData = UserData()

    override suspend fun readFrom(source: BufferedSource): UserData =
        json.decodeFromString<UserData>(source.readUtf8())

    override suspend fun writeTo(
        t: UserData,
        sink: BufferedSink,
    ) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserData.serializer(), t))
        }
    }
}
