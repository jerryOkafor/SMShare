package com.jerryokafor.smshare.model

import org.jetbrains.exposed.sql.Table

object UserSettings : Table() {
    val id = integer("id").autoIncrement()
}

data class UserSetting(
    val id: Int = -1,
)
