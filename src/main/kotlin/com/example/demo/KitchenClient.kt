package com.example.demo

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux
import java.util.*
import java.util.concurrent.TimeoutException

const val CIRCUIT_BREAKER_KITCHEN = "kitchen"

@Component
class KitchenClient {
    private val webClient: WebClient = WebClient.create("http://localhost:5000")

    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN)
    fun getDishes(): Flux<Dish> {
        return webClient.get().uri("/").retrieve().bodyToFlux(Dish::class.java)
    }

    @Retry(name = CIRCUIT_BREAKER_KITCHEN, fallbackMethod = "fallback")
    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN)
    fun failureGetDishes(): Flux<Dish> {
        return webClient.get().uri("/failure").retrieve().bodyToFlux(Dish::class.java)
    }

    @Retry(name = CIRCUIT_BREAKER_KITCHEN)
    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN, fallbackMethod = "fallback")
    fun failureGetDishesAndNotRetry(): Flux<Dish> {
        return webClient.get().uri("/failure").retrieve().bodyToFlux(Dish::class.java)
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN)
    fun slowGetDishes(): Flux<Dish> {
        return webClient.get().uri("/slow").retrieve().bodyToFlux(Dish::class.java)
    }

    @Retry(name = CIRCUIT_BREAKER_KITCHEN, fallbackMethod = "fallback")
    @TimeLimiter(name = CIRCUIT_BREAKER_KITCHEN)
    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN)
    fun timeoutGetDishesWithRetry(): Flux<Dish> {
        return webClient.get().uri("/timeout").retrieve().bodyToFlux(Dish::class.java)
    }

    @TimeLimiter(name = CIRCUIT_BREAKER_KITCHEN)
    @CircuitBreaker(name = CIRCUIT_BREAKER_KITCHEN, fallbackMethod = "fallback")
    fun timeoutGetDishes(): Flux<Dish> {
        return webClient.get().uri("/timeout").retrieve().bodyToFlux(Dish::class.java)
    }

    private fun fallback(webClientResponseException: WebClientResponseException): Flux<Dish> {
        return Flux.just(randomDish())
    }

    private fun fallback(timeoutException: TimeoutException): Flux<Dish> {
        return Flux.just(randomDish())
    }

    private fun randomDish(): Dish = cachedMenu[picker.nextInt(cachedMenu.size)]

    private val cachedMenu: List<Dish> = listOf(
        Dish("Caprese salad"),
        Dish("Basil pesto ravioli"),
        Dish("Margherita pizza"),
    )

    private val picker = Random()
}