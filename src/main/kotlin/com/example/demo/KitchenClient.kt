package com.example.demo

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class KitchenClient {
    private val webClient: WebClient = WebClient.create("http://localhost:5000")

    fun getDishes(): Flux<Dish> {
        return webClient.get().uri("/").retrieve().bodyToFlux(Dish::class.java)
    }
}