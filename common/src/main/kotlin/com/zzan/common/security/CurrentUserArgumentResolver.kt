package com.zzan.common.security

import com.zzan.common.annotation.CurrentUser
import com.zzan.common.dto.TokenUserInfo
import com.zzan.common.exception.CustomException
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class CurrentUserArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(CurrentUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw CustomException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다.")

        return when (parameter.parameterType) {
            // String 타입이면 userId만 반환
            String::class.java -> authentication.name

            // TokenUserInfo 타입이면 상세 정보 반환
            TokenUserInfo::class.java -> authentication.details as? TokenUserInfo
                ?: throw CustomException(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증 정보입니다.")

            else -> throw CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "지원하지 않는 @CurrentUser 타입입니다: ${parameter.parameterType}"
            )
        }
    }
}
