package com.ryulth.woom.security

import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils.removeStart
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher

class TokenAuthenticationFilter(
    private val requestMatcher: RequestMatcher
) : AbstractAuthenticationProcessingFilter(requestMatcher) {

    companion object {
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val param = request.getHeader(AUTHORIZATION)
        val token = removeStart(param, BEARER).trim()

        return UsernamePasswordAuthenticationToken(token, token)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        super.successfulAuthentication(request, response, chain, authResult)
        chain.doFilter(request, response)
    }
}
