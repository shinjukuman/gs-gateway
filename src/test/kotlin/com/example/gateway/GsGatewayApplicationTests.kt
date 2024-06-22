package com.example.gateway

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GsGatewayApplicationTests {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        webClient
            .get().uri("/get")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.headers.Hello").isEqualTo("World")

        webClient
            .get().uri("/delay/3")
            .header("Host", "www.circuitbreaker.com")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith { response ->
                assertThat(response.responseBody).isEqualTo("fallback".toByteArray())
            }
    }

    companion object {
        private lateinit var wireMockServer: WireMockServer

        @JvmStatic
        @BeforeAll
        fun setup() {
            wireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort())
            wireMockServer.start()

            wireMockServer.stubFor(
                get(urlEqualTo("/get"))
                    .willReturn(
                        aResponse()
                            .withBody("{\"headers\":{\"Hello\":\"World\"}}")
                            .withHeader("Content-Type", "application/json")
                    )
            )
            wireMockServer.stubFor(
                get(urlEqualTo("/delay/3"))
                    .willReturn(
                        aResponse()
                            .withBody("no fallback")
                            .withFixedDelay(3000)
                    )
            )
        }

        @JvmStatic
        @AfterAll
        fun teardown() {
            wireMockServer.stop()
        }

        @JvmStatic
        @DynamicPropertySource
        fun dynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("uri.httpbin") { "http://localhost:${wireMockServer.port()}" }
        }
    }
}
