package com.zzan.feed.application.port.out

import com.zzan.common.dto.BaseUserInfo

interface FeedUserInfoProvider {
    fun getBasicUserInfo(userId: String): BaseUserInfo
}
