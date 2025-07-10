package com.jerryokafor.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerryokafor.smshare.core.model.ProfileLocal
import com.jerryokafor.smshare.core.model.UserProfile

data class UserProfileLocalEntity(
    val country: String? = null,
    val language: String? = null
) {
    companion object {
        fun fromDomainModel(profileLocal: ProfileLocal?): UserProfileLocalEntity =
            UserProfileLocalEntity(
                country = profileLocal?.country,
                language = profileLocal?.language
            )
    }
}

fun UserProfileLocalEntity.toDomainModel(): ProfileLocal = ProfileLocal(
    country = country,
    language = language
)

@Entity("userProfiles")
data class UserProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: String? = null,
    val name: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val picture: String? = null,
    val email: String? = null,

    @Embedded(prefix = "userProfile_")
    val locale: UserProfileLocalEntity? = null,
) {
    companion object {
        fun fromDomainEntity(userProfile: UserProfile?): UserProfileEntity = UserProfileEntity(
            subjectId = userProfile?.subjectId,
            name = userProfile?.name,
            givenName = userProfile?.givenName,
            familyName = userProfile?.familyName,
            picture = userProfile?.picture,
            email = userProfile?.email,
            locale = UserProfileLocalEntity.fromDomainModel(userProfile?.locale)
        )
    }
}

fun UserProfileEntity.toDomainModel(): UserProfile = UserProfile(
    subjectId = subjectId,
    name = name,
    givenName = givenName,
    familyName = familyName,
    picture = picture,
    email = email,
    locale = locale?.toDomainModel()
)


