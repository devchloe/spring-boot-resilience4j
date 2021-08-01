package com.example.demo

data class Dish(val description: String, val delivered: Boolean = false)

fun Dish.deliver(): Dish = Dish(this.description, true) // 함수형 프로그래밍에서는 기존 객체의 상태를 바꾸지 않고 변환된 상태를 가진 새 객체를 만들어 사용한다.
