package com.jerryokafor.smshare.core.domain.mapping

import com.jerryokafor.smshare.core.model.UserProfile
import com.jerryokafor.smshare.core.network.response.XUserLookUpMeResponse


fun XUserLookUpMeResponse.toUserProfile() = UserProfile(
    subjectId = this.data?.id,
    name = this.data?.name,
    givenName = this.data?.name,
    familyName = this.data?.name,
    picture = this.data?.profileImageUrl,
    email = this.data?.email,
    locale = null
)
