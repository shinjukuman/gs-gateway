package com.example.gateway.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties
class UriProperties {
    var httpbin: String = "http://httpbin.org:80"
}
