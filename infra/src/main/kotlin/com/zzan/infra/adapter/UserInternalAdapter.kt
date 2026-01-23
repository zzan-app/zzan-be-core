package com.zzan.infra.adapter

import com.zzan.common.dto.BaseUserInfo
import com.zzan.feed.application.port.out.FeedUserInfoProvider
import com.zzan.liquor.application.port.out.LiquorUserInfoProvider
import com.zzan.user.application.port.`in`.UserUseCase
import org.springframework.stereotype.Component

@Component
class UserInternalAdapter(
    private val userUseCase: UserUseCase,
) : FeedUserInfoProvider, LiquorUserInfoProvider {
    override fun getBasicUserInfo(userId: String): BaseUserInfo {
        return userUseCase.getBasicUserInfo(userId)
    }
}
