package com.example.gateway

import com.example.gateway.properties.UriProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(UriProperties::class)
@SpringBootApplication
class GsGatewayApplication

fun main(args: Array<String>) {
    runApplication<GsGatewayApplication>(*args)
}
