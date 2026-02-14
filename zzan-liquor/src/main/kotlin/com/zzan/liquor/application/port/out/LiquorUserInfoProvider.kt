package com.zzan.liquor.application.port.out

import com.zzan.common.dto.BaseUserInfo

interface LiquorUserInfoProvider {
    fun getBasicUserInfo(userId: String): BaseUserInfo
}
