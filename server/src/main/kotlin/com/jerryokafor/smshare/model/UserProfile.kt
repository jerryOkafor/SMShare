package com.jerryokafor.smshare.model

import org.jetbrains.exposed.sql.Table

object UserProfiles : Table() {
    val id = integer("id").autoIncrement()
}


data class UserProfile(val id: Int = -1)
