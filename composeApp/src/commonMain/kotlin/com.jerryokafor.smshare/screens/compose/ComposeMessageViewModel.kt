package com.jerryokafor.smshare.screens.compose

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryokafor.smshare.channel.LifecycleState
import com.jerryokafor.smshare.channel.MemberNetworkVisibility
import com.jerryokafor.smshare.channel.ShareCommentary
import com.jerryokafor.smshare.channel.ShareContent
import com.jerryokafor.smshare.channel.ShareMediaCategory
import com.jerryokafor.smshare.channel.SharePost
import com.jerryokafor.smshare.channel.UGCShareContent
import com.jerryokafor.smshare.channel.Visibility
import com.jerryokafor.smshare.core.domain.AccountRepository
import com.jerryokafor.smshare.core.model.Account
import com.jerryokafor.smshare.core.model.AccountAndProfile
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ComposeMessageViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel(),
    KoinComponent {
    private val accountRepository: AccountRepository by inject()
    private val httpClient: HttpClient by inject()

    private val accountId = savedStateHandle.get<Long>("accountId")
    private val selectedAccountIds = MutableStateFlow(setOf(accountId))

    val uiState = combine(
        accountRepository.accountsAndProfiles(),
        selectedAccountIds,
    ) { accountsAndProfiles, accountIds ->
        val updateAccountsAndProfiles = accountsAndProfiles.map { accountAndProfile ->
            val account = accountAndProfile.account
            val isSelected = accountIds.any { it == account.id }
            accountAndProfile.copy(
                account = account.copy(isSelectedForCompose = isSelected)
            )
        }
        ComposeMessageUiState(targetAccountAndProfiles = updateAccountsAndProfiles)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = ComposeMessageUiState(),
    )

    fun toggleTargetChannelSelection(account: Account) {
        if (account.isSelectedForCompose)
            selectedAccountIds.update { it.minusElement(account.id) }
        else
            selectedAccountIds.update { it.plusElement(account.id) }
    }

    fun postMassage(message: SMMessage) {
        viewModelScope.launch {
            val sharePost = SharePost(
                author = "urn:li:person:8675309",
                lifecycleState = LifecycleState.PUBLISHED,
                specificContent = ShareContent(
                    ugcShareContent = UGCShareContent(
                        shareCommentary = ShareCommentary("This is a sample message for testing"),
                        shareMediaCategory = ShareMediaCategory.NONE,
                        media = emptyList()
                    )
                ),
                visibility = Visibility(ugcMemberNetworkVisibility = MemberNetworkVisibility.PUBLIC)
            )

            val jsonString = Json.encodeToString(sharePost)

            println("SharePost: $jsonString")

            postUserGeneratedContent(accessToken = "", share = sharePost)
        }
    }

    private suspend fun postUserGeneratedContent(accessToken: String, share: SharePost) {
        val response = httpClient.post("https://api.linkedin.com/v2/ugcPosts") {
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, "Bearer $accessToken")
                append("X-Restli-Protocol-Version", "2.0.0")
            }
            contentType(ContentType.Application.Json)
            setBody(share)
        }.body<Any>()

        println("response: $response")
    }
}


data class SMMessage(val content: String)

data class ComposeMessageUiState(
    val targetAccountAndProfiles: List<AccountAndProfile> = emptyList(),
)


