package com.ryulth.woom.util

import com.ryulth.woom.dto.ErrorResponse
import mu.KLogging
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestControllerExceptionHandler {

    companion object : KLogging()

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(e: BadCredentialsException): ErrorResponse {
        logger.error { "BadCredentialsException $e" }
        e.printStackTrace()
        val httpStatus = UNAUTHORIZED
        return ErrorResponse(
            status = httpStatus.value(),
            error = httpStatus.reasonPhrase,
            message = e.message ?: e.javaClass.simpleName
        )
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ErrorResponse {
        logger.error { "IllegalArgumentException $e" }
        e.printStackTrace()
        val httpStatus = BAD_REQUEST
        return ErrorResponse(
            status = httpStatus.value(),
            error = httpStatus.reasonPhrase,
            message = e.message ?: e.javaClass.simpleName
        )
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        logger.error { "Exception $e" }
        e.printStackTrace()
        val httpStatus = INTERNAL_SERVER_ERROR
        return ErrorResponse(
            status = httpStatus.value(),
            error = httpStatus.reasonPhrase,
            message = e.message ?: e.javaClass.simpleName
        )
    }
}
