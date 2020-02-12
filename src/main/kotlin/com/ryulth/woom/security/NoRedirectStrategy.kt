package com.ryulth.woom.security

import org.springframework.security.web.RedirectStrategy
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class NoRedirectStrategy : RedirectStrategy {
    @Throws(IOException::class)
    override fun sendRedirect(request: HttpServletRequest, response: HttpServletResponse, url: String) {

    }
}