package com.ryulth.woom.controller

import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @ApiOperation(value = "Health Checker", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String> = ResponseEntity.ok("OK")
}