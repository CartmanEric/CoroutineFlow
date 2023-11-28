package com.sumin.coroutineflow.lesson13

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    //в данном примере если не использовать buffer() то поток будет ждать collect  и только
    // после того как он получит его пойдет emit следуюшего значения
    // указывае buffer() (по дефолту он идет на 64 обьекта) то он будет эмитить такое же
    // количество элементов, и после переполнения будет ждать пока их обработают.
    // у buffer() вторым параметром BufferOverflow.DROP_OLDEST BufferOverflow.DROP_LATEST
    val job = scope.launch {
        val flow: Flow<Int> = flow {
            repeat(10) {
                println("Emitted: $it")
                emit(it)
                println("After emit: $it")
                delay(200)
            }
        }.buffer()
        flow.collect {
            println("Collected: $it")
            delay(1000)
        }
    }

    job.join()
}
