package com.jerryokafor.smshare.core.domain

import com.jerryokafor.core.database.entity.AccountEntity
import com.jerryokafor.core.database.entity.AccountTypeEntity
import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.model.UserProfile
import com.jerryokafor.smshare.core.network.response.TokenResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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

@OptIn(ExperimentalTime::class)
fun ChannelConfig?.toAccountEntity(
    tokenResponse: TokenResponse?,
    subjectId: String
): AccountEntity = AccountEntity(
    type = AccountTypeEntity.fromDomainModel(this?.accountType),
    name = this?.name ?: "",
    description = this?.description ?: "",
    accessToken = tokenResponse?.accessToken ?: "",
    expiresInt = tokenResponse?.expiresIn ?: 0,
    scope = tokenResponse?.scope ?: "",
    created = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        .format(
            format = LocalDateTime.Format {
                date(LocalDate.Formats.ISO)
                char(' ')
                time(LocalTime.Formats.ISO)
            }
    ),
    subjectId = subjectId
)
