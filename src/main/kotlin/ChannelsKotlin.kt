package com.haris
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() = runBlocking(CoroutineName("main")) {
    val channel = Channel<Int>()
    launch(CoroutineName("v1voroutine")) {
        println("sending from ${Thread.currentThread().name}")
        for (x in 1..5) channel.send(x*x)
//        send() : mengirim keluar data dari channel
    }

    repeat(5) {println(channel.receive())}
//    receive() : menerima data dari channel
    println("received in ${Thread.currentThread().name}")
}