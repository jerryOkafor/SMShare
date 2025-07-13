package com.jerryokafor.smshare.core.domain.mapping

import com.jerryokafor.smshare.core.model.ProfileLocal
import com.jerryokafor.smshare.core.model.UserProfile
import com.jerryokafor.smshare.core.network.response.LinkedInUserInfoLocal
import com.jerryokafor.smshare.core.network.response.LinkedInUserInfoResponse

fun LinkedInUserInfoResponse.toUserProfile(): UserProfile = UserProfile(
    subjectId = subjectId!!,
    name = name,
    givenName = givenName,
    familyName = familyName,
    picture = picture,
    email = email,
    locale = locale.toProfileLocale()
)

private fun LinkedInUserInfoLocal?.toProfileLocale(): ProfileLocal =
    ProfileLocal(country = this?.country ?: "", language = this?.language ?: "")
