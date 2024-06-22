package com.example.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GsGatewayApplication {
    @Bean
    fun myRoutes(builder: RouteLocatorBuilder): RouteLocator {
        return builder
            .routes()
            .route("httpbin_route") { p ->
                p.path("/get")
                    .filters { f -> f.addRequestHeader("hello", "world") }
                    .uri("http://httpbin.org:80")
            }
            .route { p ->
                p.host("*.circuitbreaker.com")
                    .filters { f ->
                        f.circuitBreaker { config ->
                            config.setName("mycmd").setFallbackUri("forward:/fallback")
                        }
                    }
                    .uri("http://httpbin.org:80")
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<GsGatewayApplication>(*args)
}
