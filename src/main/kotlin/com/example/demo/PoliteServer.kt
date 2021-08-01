package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class PoliteServer(private val kitchen: KitchenService, private val kitchenClient: KitchenClient) {
    @GetMapping()
    fun serveDishes(): Flux<Dish> {
        return kitchen.getDishes().map { it.deliver() }
    }

    @GetMapping("/remote")
    fun serveDishesFromRemote(): Flux<Dish> {
        return kitchenClient.getDishes().map { it.deliver() }
    }
}