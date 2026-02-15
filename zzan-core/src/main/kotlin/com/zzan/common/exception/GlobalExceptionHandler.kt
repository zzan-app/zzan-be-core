package com.zzan.common.exception

import com.zzan.common.response.ApiResponse
import jakarta.validation.ConstraintViolationException
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@ControllerAdvice
class GlobalExceptionHandler : KLogging() {

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException): ResponseEntity<ApiResponse<Nothing>> {
        logger.warn("Resource not found: ${e.message}")

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error("요청한 리소스를 찾을 수 없습니다"))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        val errorMessage = e.bindingResult.fieldErrors.joinToString(", ") {
            it.defaultMessage ?: "잘못된 입력입니다"
        }

        logger.warn("Validation failed: $errorMessage")

        val errorResponse = ApiResponse.error<Nothing>(errorMessage)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<ApiResponse<Nothing>> {
        val errorMessage = e.constraintViolations.joinToString(", ") {
            it.message
        }

        logger.warn("Constraint violation: $errorMessage")

        val errorResponse = ApiResponse.error<Nothing>(errorMessage)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ApiResponse<Nothing>> {
        if (e.status.is5xxServerError) {
            logger.error("Custom exception occurred: ${e.message}", e)
        } else {
            logger.warn("Custom exception occurred: ${e.message}")
        }

        return ResponseEntity
            .status(e.status)
            .body(ApiResponse.error(e.message ?: "오류가 발생했습니다"))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ApiResponse<Nothing>> {
        logger.warn("Invalid argument: ${e.message}")

        val errorResponse = ApiResponse.error<Nothing>(e.message ?: "잘못된 요청입니다")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnhandledException(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        logger.error("Unhandled exception occurred: ${e.message}", e)

        val errorResponse = ApiResponse.error<Nothing>("서버 내부 오류가 발생했습니다")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse)
    }
}
