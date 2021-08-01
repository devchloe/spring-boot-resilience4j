package com.example.demo

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
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

    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN)
    fun failureGetDishes(): Flux<Dish> {
        return webClient.get().uri("/failure").retrieve().bodyToFlux(Dish::class.java)
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN)
    fun slowGetDishes(): Flux<Dish> {
        return webClient.get().uri("/slow").retrieve().bodyToFlux(Dish::class.java)
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN)
    fun timeoutGetDishes(): Flux<Dish> {
        return webClient.get().uri("/timeout").retrieve().bodyToFlux(Dish::class.java)
    }
}