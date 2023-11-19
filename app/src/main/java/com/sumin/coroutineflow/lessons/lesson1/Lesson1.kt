package com.sumin.coroutineflow.lessons.lesson1

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map

//чтобы запустить поток нужен терминальный оператор, в других случаях он не запустится
// .filter  .map это промежуточные операторы
// при потоке данных каждый элемент колекции будет проходить все промежуточные операторы,
// и после того как обьект прошел начинает проходить второй элемент.
//если брать обычный лист то результат выйдет сразу как все елементы прогонятся.
suspend fun main() { // .asFlow() создает из колекции поток данных
    val numbers = listOf(3, 4, 8, 16, 5, 7, 11, 32, 41, 28, 43, 47, 84, 116, 53, 59, 61).asFlow()
    numbers.filter { it.isPrime() }
        .filter { it > 20 }
        .map {
            println("Map")
            "Number: $it"
        }
      // метод  .collect полная копия forEach
        .collect { println(it) }
}

suspend fun Int.isPrime(): Boolean {
    if (this <= 1) return false
    for (i in 2..this / 2) {
        delay(50)
        if (this % i == 0) return false
    }
    return true
}
