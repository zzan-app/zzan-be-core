package com.zzan.user.domain.vo

data class Phone(
    val value: String
) {
    init {
        require(value.matches(Regex("^01[0-9]{8,9}$"))) {
            "유효하지 않은 전화번호 형식입니다. 숫자만 10~11자리로 입력해주세요."
        }
    }
}
