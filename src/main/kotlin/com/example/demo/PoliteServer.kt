package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class PoliteServer(private val kitchen: KitchenService) {
    @GetMapping()
    fun serveDishes(): Flux<Dish> {
        return kitchen.getDishes().map { it.deliver() }
    }
}