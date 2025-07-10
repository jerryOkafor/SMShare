package com.jerryokafor.smshare.core.domain

import com.jerryokafor.core.database.entity.AccountEntity
import com.jerryokafor.core.database.entity.AccountTypeEntity
import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.model.UserProfile
import com.jerryokafor.smshare.core.network.response.TokenResponse
import kotlinx.datetime.*

/**
 * Interface for channel configuration, represents various social media platforms
 * that a user may want to add to the app.
 *
 */
interface ChannelConfig {
    val accountType: AccountType

    val name: String

    val description: String

    fun createOAuthUrl(
        state: String,
        challenge: String,
        redirectUrl: String,
    ): String

    suspend fun exchangeCodeForAccessToken(
        code: String,
        challenge: String,
        redirectUrl: String,
    ): TokenResponse

    suspend fun userProfile(accessToken: String): UserProfile
}

fun ChannelConfig?.toAccountEntity(tokenResponse: TokenResponse?): AccountEntity = AccountEntity(
    type = AccountTypeEntity.fromDomainModel(this?.accountType),
    name = this?.name ?: "",
    description = this?.description ?: "",
    accessToken = tokenResponse?.accessToken ?: "",
    expiresInt = tokenResponse?.expiresIn ?: 0,
    scope = tokenResponse?.scope ?: "",
    created = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).format(
        LocalDateTime.Formats.ISO
    ),
)
