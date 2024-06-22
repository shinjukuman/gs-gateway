package com.example.gateway.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class FallbackController {
    @RequestMapping("/fallback")
    fun fallback(): Mono<String> {
        return Mono.just("fallback")
    }
}
