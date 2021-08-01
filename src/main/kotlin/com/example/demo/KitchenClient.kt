package com.example.demo

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

const val CIRCUIT_BREAKER_KITCHEN = "kitchen"

@Component
class KitchenClient {
    private val webClient: WebClient = WebClient.create("http://localhost:5000")


    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN)
    fun getDishes(): Flux<Dish> {
        return webClient.get().uri("/").retrieve().bodyToFlux(Dish::class.java)
    }
}