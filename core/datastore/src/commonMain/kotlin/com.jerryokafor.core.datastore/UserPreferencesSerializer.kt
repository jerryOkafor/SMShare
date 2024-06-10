package com.jerryokafor.core.datastore

import androidx.datastore.core.okio.OkioSerializer
import okio.BufferedSink
import okio.BufferedSource
import okio.use

internal object UserPreferencesSerializer : OkioSerializer<UserData> {
    override val defaultValue: UserData = UserData()

    override suspend fun readFrom(source: BufferedSource): UserData {
        return json.decodeFromString<UserData>(source.readUtf8())
    }

    override suspend fun writeTo(
        t: UserData,
        sink: BufferedSink,
    ) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserData.serializer(), t))
        }
    }
}
