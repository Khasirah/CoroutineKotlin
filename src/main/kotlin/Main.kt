package com.haris

import kotlinx.coroutines.*
import java.util.concurrent.CancellationException
import kotlin.system.measureTimeMillis

// anotasi @InternalCoroutinesApi u/ menggunakan internal coroutine API krn masih tahap experimen
// seperti getCancellationException()
@InternalCoroutinesApi
fun main() = runBlocking{
    launch {
        delay(1000L)
        println("Coroutines!")
    }
    println("Hello,")
    delay(2000L)

    val timeOne = measureTimeMillis {
        val capital = getCapital()
        val income = getIncome()
        println("Your profit is ${income - capital}")
    }

    val timeTwo = measureTimeMillis {
        val capital = async { getCapital() }
        val income = async { getIncome() }
        println("Your profit is ${income.await() - capital.await()}")
    }
//    await() : pasangan dari async u/ mengakses nilai dari async function (deffered)

    println("Completed in $timeOne ms vs $timeTwo ms")

//    pembuatan job
    val job = launch(start = CoroutineStart.LAZY) {
        delay(1000L)
        println("Start new job")
    }
    val job3 = launch {
        delay(5000)
        println("job 5 detik")
    }
    job.start()
    job.join()
    job3.start()
    delay(2000)
    job3.cancel(cause = CancellationException("lama kamu bang"))
    println("other task")
//    CoroutineStart.LAZY menjalankan job saat dibutuhkan
//    start() : menjalankan job tanpa harus menunggu job selesai
//    join() : menjalankan job menunggu job selesai terlebih dahulu
//    cancel() : membatalkan job, job yg sedang berlangsung saja yg dapat dibatalkan
//    job3 membutuhkan waktu 5 detik, namun ketika mencapai waktu 2 detik job telah dibatalkan
//    cancel() dengan parameter cause
    if (job3.isCancelled){
        println("Job is cancelled")
        println("${job3.getCancellationException().message}")
//        mengakses parameter pesan dari parameter cause
    }
//    setelah job3 dibatalkan dia akan memiliki status cancelled dan completed
//    apabile terdapat job baru yg blm dijalankan kemudian dipanggil fungsi cancel
//    maka job tersebut akan langsung masuk status cancelled tanpa melalui status cancelling
    val job2 = Job()
}

// runBlocking : untuk memulai coroutine utama
// launch : untuk menjalankan coroutine baru
// delay : untuk menunda kode berikutnya
// delay : suspending function yg tidak akan memblokir sebuah thread

suspend fun getCapital(): Int {
    delay(1000L)
    return 50000
}

suspend fun getIncome(): Int {
    delay(1000L)
    return 75000
}