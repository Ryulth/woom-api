package com.ryulth.woom.security

import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.web.RedirectStrategy

class NoRedirectStrategy : RedirectStrategy {
    @Throws(IOException::class)
    override fun sendRedirect(request: HttpServletRequest, response: HttpServletResponse, url: String) {
    }
}
