package com.zzan.liquor.application.port.out

import com.zzan.core.dto.BaseUserInfo

interface LiquorUserInfoProvider {
    fun getBasicUserInfo(userId: String): BaseUserInfo
}
