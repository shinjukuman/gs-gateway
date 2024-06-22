package com.example.gateway.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "uri")
class UriProperties {
    lateinit var httpbin: String
}
