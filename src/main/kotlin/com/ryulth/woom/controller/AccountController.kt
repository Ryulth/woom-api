package com.ryulth.woom.controller

import com.ryulth.woom.dto.AppleLoginRequest
import com.ryulth.woom.dto.AppleRegisterRequest
import com.ryulth.woom.dto.EmailLoginRequest
import com.ryulth.woom.dto.EmailRegisterRequest
import com.ryulth.woom.dto.KakaoLoginRequest
import com.ryulth.woom.dto.KakaoRegisterRequest
import com.ryulth.woom.dto.Token
import com.ryulth.woom.security.TokenProvider
import com.ryulth.woom.service.AppleAccountService
import com.ryulth.woom.service.EmailAccountService
import com.ryulth.woom.service.KakaoAccountService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AccountController(
    private val emailAccountService: EmailAccountService,
    private val kakaoAccountService: KakaoAccountService,
    private val appleAccountService: AppleAccountService,
    private val tokenProvider: TokenProvider
) {

    @ApiOperation(
        value = "Update Access Token API",
        notes = "refresh_token 을 보내면 access_token 을 반환해주는 API. (Authorization Header 필요 없습니다. Swagger 전역 설정 원인)"
    )
    @GetMapping("/refresh/{token:.+}")
    fun refreshToken(@PathVariable("token") refreshToken: String): ResponseEntity<Token> {
        return ResponseEntity.ok(
            tokenProvider.refreshToken(refreshToken)
        )
    }

    @ApiOperation(
        value = "Update Access Token API",
        notes = "access_token 을 보내면 토큰 상태를 반환해주는 API. (Authorization Header 필요 없습니다. Swagger 전역 설정 원인)"
    )
    @GetMapping("/verify/{token:.+}")
    fun verifyToken(@PathVariable("token") accessToken: String): ResponseEntity<String> {
        tokenProvider.verifyToken(accessToken, true)
        return ResponseEntity.ok().body("OK")
    }

    @ApiOperation(value = "Email 회원가입 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/register/email")
    fun registerWithEmail(@RequestBody emailRegisterRequest: EmailRegisterRequest): ResponseEntity<Token> {
        return ResponseEntity.ok(emailAccountService.register(emailRegisterRequest))
    }

    @ApiOperation(value = "Email 로그인 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/login/email")
    fun loginWithEmail(@RequestBody emailLoginRequest: EmailLoginRequest): ResponseEntity<Token> {
        return ResponseEntity.ok(emailAccountService.login(emailLoginRequest))
    }

    @ApiOperation(value = "Kakao 회원가입 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/register/kakao")
    fun registerWithKakao(@RequestBody kakaoRegisterRequest: KakaoRegisterRequest): ResponseEntity<Token> {
        return ResponseEntity.ok(kakaoAccountService.register(kakaoRegisterRequest))
    }

    @ApiOperation(value = "Kakao 로그인 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/login/kakao")
    fun loginWithKakao(@RequestBody kakaoLoginRequest: KakaoLoginRequest): ResponseEntity<Token> {
        return ResponseEntity.ok(kakaoAccountService.login(kakaoLoginRequest))
    }

    @ApiOperation(value = "Apple 회원가입 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/register/apple")
    fun registerWithApple(@RequestBody appleRegisterRequest: AppleRegisterRequest): ResponseEntity<Token> {
        return ResponseEntity.ok(appleAccountService.register(appleRegisterRequest))
    }

    @ApiOperation(value = "Apple 로그인 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/login/apple")
    fun loginWithApple(@RequestBody appleLoginRequest: AppleLoginRequest): ResponseEntity<Token> {
        return ResponseEntity.ok(appleAccountService.login(appleLoginRequest))
    }
}
