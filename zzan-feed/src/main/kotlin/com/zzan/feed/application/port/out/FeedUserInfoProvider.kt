package com.zzan.feed.application.port.out

import com.zzan.core.dto.BaseUserInfo

interface FeedUserInfoProvider {
    fun getBasicUserInfo(userId: String): BaseUserInfo
}
