package com.haris
import kotlinx.coroutines.*

@ObsoleteCoroutinesApi
fun main() = runBlocking<Unit> {
    val dispatcher = newFixedThreadPoolContext(3, "thread")
    val dispatcher2 = newSingleThreadContext("thread2")
//    newSingleThreadContext() : penentuan thread manul
//    newFixedThreadPoolContext() : menentukan jumlah thread yg dapat digunakan
//    Runtime akan menentukan thread mana yg available
    launch(dispatcher) {
        println("starting ${Thread.currentThread().name}")
        delay(2000)
        println("resuming ${Thread.currentThread().name}")
    }.start()
}
