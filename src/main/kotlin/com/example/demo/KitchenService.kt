package com.example.demo

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*

@Service
class KitchenService {
    fun getDishes(): Flux<Dish> {
        return Flux.just(randomDish())
    }

    private fun randomDish(): Dish = menu[picker.nextInt(menu.size)]

    private val menu: List<Dish> = listOf(
        Dish("Sesame chicken"),
        Dish("Lo mein noodles, plain"),
        Dish("Sweet & sour beef")
    )

    private val picker = Random()
}
