package com.ryulth.woom.security

import com.ryulth.woom.dto.LoginType
import com.ryulth.woom.dto.Token
import com.ryulth.woom.dto.UserSession
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import java.nio.charset.StandardCharsets
import java.util.Date
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret.base64.access}")
    private val accessSecretKey: String,
    @Value("\${jwt.secret.base64.refresh}")
    private val refreshSecretKey: String,
    @Value("\${jwt.expiration-seconds.access}")
    private val accessTokenExpirationSeconds: Int,
    @Value("\${jwt.expiration-seconds.refresh}")
    private val refreshTokenExpirationSeconds: Int
) : TokenProvider {

    companion object : KLogging() {
        const val AUTHORITIES_ID = "userId"
        const val AUTHORITIES_UM_ID = "woomId"
        const val AUTHORITIES_LOGIN_TYPE = "loginType"
        const val BEARER = "Bearer"
    }

    override fun generatedToken(userSession: UserSession): Token {
        return Token(
            accessToken = newToken(userSession, true),
            refreshToken = newToken(userSession, false),
            type = BEARER
        )
    }

    private fun newToken(userSession: UserSession, isAccessToken: Boolean): String {
        val now = Date().time
        val validity =
            if (isAccessToken) Date(now + this.accessTokenExpirationSeconds * 1000)
            else Date(now + this.refreshTokenExpirationSeconds * 1000)
        val key = if (isAccessToken) this.accessSecretKey else this.refreshSecretKey

        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .claim(AUTHORITIES_ID, userSession.userId)
            .claim(AUTHORITIES_UM_ID, userSession.woomId)
            .claim(AUTHORITIES_LOGIN_TYPE, userSession.loginType)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, this.generateKey(key))
            .compact()
    }

    override fun verifyToken(token: String, isAccess: Boolean): UserSession {
        val key: String = if (isAccess) this.accessSecretKey else this.refreshSecretKey
        var message = ""
        try {
            Jwts.parser().setSigningKey(this.generateKey(key)).parseClaimsJws(token)
            val claims = Jwts.parser().setSigningKey(this.generateKey(key))
                .parseClaimsJws(token).getBody()
            return UserSession(
                userId = (claims[AUTHORITIES_ID] as Int).toLong(),
                woomId = (claims[AUTHORITIES_UM_ID].toString()),
                loginType = LoginType.valueOf(claims[AUTHORITIES_LOGIN_TYPE].toString())
            )
        } catch (e: SecurityException) {
            logger.error { "Invalid JWT signature trace: $e" }
            message = e.message!!
        } catch (e: MalformedJwtException) {
            logger.error { "Invalid JWT signature trace: $e" }
            message = e.message!!
        } catch (e: ExpiredJwtException) {
            logger.error { "Expired JWT token trace: $e" }
            message = e.message!!
        } catch (e: UnsupportedJwtException) {
            logger.error { "Unsupported JWT token trace: $e" }
            message = e.message!!
        } catch (e: IllegalArgumentException) {
            logger.error { "JWT token compact of handler are invalid trace: $e" }
            message = e.message!!
        } catch (e: JwtException) {
            logger.error { "JWT token are invalid trace: $e" }
            message = e.message!!
        }
        throw BadCredentialsException(message)
    }

    override fun refreshToken(refreshToken: String): Token {
        val userSession: UserSession = this.verifyToken(refreshToken, false)
        return this.generatedToken(userSession)
    }

    private fun generateKey(secretKey: String): ByteArray? {
        var key: ByteArray? = null
        key = secretKey.toByteArray(StandardCharsets.UTF_8)
        return key
    }
}
