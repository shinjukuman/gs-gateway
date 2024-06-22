package com.example.gateway

import com.example.gateway.properties.UriProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(UriProperties::class)
@SpringBootApplication
class GsGatewayApplication {
    @Bean
    fun myRoutes(builder: RouteLocatorBuilder, uriProperties: UriProperties): RouteLocator {
        return builder
            .routes()
            .route("httpbin_route") { p ->
                p.path("/get")
                    .filters { f -> f.addRequestHeader("hello", "world") }
                    .uri(uriProperties.httpbin)
            }
            .route("circuitbreaker_route") { p ->
                p.host("*.circuitbreaker.com")
                    .filters { f ->
                        f.circuitBreaker { config ->
                            config.setName("mycmd").setFallbackUri("forward:/fallback")
                        }
                    }
                    .uri(uriProperties.httpbin)
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<GsGatewayApplication>(*args)
}
