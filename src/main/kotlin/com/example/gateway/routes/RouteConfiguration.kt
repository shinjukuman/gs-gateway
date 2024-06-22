package com.example.gateway.routes

import com.example.gateway.properties.UriProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(UriProperties::class)
class RouteConfiguration {
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
