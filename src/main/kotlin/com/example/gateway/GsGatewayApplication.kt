package com.example.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GsGatewayApplication

fun main(args: Array<String>) {
    runApplication<GsGatewayApplication>(*args)
}
