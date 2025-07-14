package com.jerryokafor.smshare.core.domain

import com.apollographql.apollo.ApolloClient
import com.jerryokafor.core.database.dao.AccountsDao
import com.jerryokafor.core.database.entity.UserProfileEntity
import com.jerryokafor.smshare.core.model.Account
import com.jerryokafor.smshare.core.model.UserProfile
import com.jerryokafor.smshare.core.network.response.TokenResponse
import kotlinx.coroutines.CoroutineDispatcher

interface AccountRepository {
    suspend fun addAccount(
        channelConfig: ChannelConfig,
        tokenResponse: TokenResponse,
        userProfile: UserProfile
    )

    suspend fun removeAccount(account: Account)

}

class DefaultAccountRepository(
    private val accountDao: AccountsDao,
    private val apolloClient: ApolloClient,
    private val ioDispatcher: CoroutineDispatcher,
) : AccountRepository {

    override suspend fun addAccount(
        channelConfig: ChannelConfig,
        tokenResponse: TokenResponse,
        userProfile: UserProfile
    ) {
        val accountEntity = channelConfig.toAccountEntity(tokenResponse, userProfile.subjectId)
        val userProfileEntity = UserProfileEntity.fromDomainEntity(userProfile)
        accountDao.insertAccountAndUserProfileEntity(accountEntity, userProfileEntity)
    }

    override suspend fun removeAccount(account: Account) {
        accountDao.deleteById(account.id)
    }

}


