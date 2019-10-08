package com.rakaadinugroho.coroutinesplayground

import android.util.Log
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val orderChannel = Channel<String>()

    @Test
    fun `channel sample`(){
        runBlocking {
            val listOfOrder = listOf("Adi", "Arman", "Ayu", "Reza", "Nur", "Ricardo")

            launch(context = CoroutineName("Cashier")) {
                listOfOrder.forEach { name ->
                    orderChannel.send(name)
                }
            }

            launch(context = CoroutineName("Bima")) {
                orderChannel.consumeEach { name ->
                    delay(1000)
                    println("Order by $name, Processed by ${Thread.currentThread().name}")
                }
            }

            launch(context = CoroutineName("Ayu")) {
                orderChannel.consumeEach { name ->
                    delay(500)
                    println("Order by $name, Processed by ${Thread.currentThread().name}")
                }
            }
        }
    }


    @Test
    fun `flow is sequential`() {
        runBlocking {
            val categoryNumber: Flow<String> = flow {
                println("get from cart of x")
                delay(1000)
                emit("loaded cart")

                println("get cart amount data of x")
                delay(500)
                emit("loaded amount")

                println("calculate bill")
                delay(1000)
                emit("bill created")
            }

            categoryNumber.collect {
                println("--consume $it")
            }
        }
    }
}
