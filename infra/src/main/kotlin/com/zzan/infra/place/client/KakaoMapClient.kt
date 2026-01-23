package com.zzan.infra.place.client

import com.zzan.common.exception.CustomException
import com.zzan.infra.place.dto.KakaoPlaceSearchResult
import mu.KLogging
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
@EnableConfigurationProperties(KakaoMapProperties::class)
class KakaoMapClient(
    private val kakaoMapProperties: KakaoMapProperties
) : KLogging() {
    fun searchPlaces(keyword: String, page: Int, size: Int): KakaoPlaceSearchResult {
        return call("카카오 장소 검색에 실패했습니다") {
            val uri = UriComponentsBuilder
                .fromHttpUrl(KAKAO_PLACE_SEARCH_URL)
                .queryParam("query", keyword)
                .queryParam("page", page)
                .queryParam("size", size)
                .encode(Charsets.UTF_8)
                .build()
                .toUri()

            restTemplate.exchange(
                uri,
                HttpMethod.GET,
                createRequest(),
                KakaoPlaceSearchResult::class.java
            ).body
        }
    }

    private fun createRequest(): HttpEntity<String> {
        val headers = HttpHeaders().apply {
            set("Authorization", "KakaoAK ${kakaoMapProperties.restKey}")
        }
        return HttpEntity(headers)
    }

    private fun <T> call(errorMessage: String, block: () -> T?): T {
        return try {
            block() ?: throw CustomException(HttpStatus.BAD_REQUEST, errorMessage)
        } catch (ex: HttpClientErrorException) {
            logger.error { "Kakao Map API Error: ${ex.statusCode} - ${ex.responseBodyAsString}" }
            throw CustomException(HttpStatus.BAD_REQUEST, errorMessage)
        } catch (ex: RestClientException) {
            logger.error { "Kakao Map API RestClientException: ${ex.message}" }
            throw CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "외부 서버 통신 오류: ${ex.message}")
        }
    }

    companion object {
        private const val KAKAO_PLACE_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json"
        private val restTemplate = RestTemplate()
    }
}
