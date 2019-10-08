package com.rakaadinugroho.coroutinesplayground.ui.queue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rakaadinugroho.coroutinesplayground.R
import kotlinx.android.synthetic.main.activity_queue.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

class QueueActivity : AppCompatActivity(), CoroutineScope{
    val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val orderChannel = Channel<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)

        add_to_queue.setOnClickListener {
            val name = input_name.text.toString()
            launch(context = CoroutineName("cashier")) {
                orderChannel.send(name)
            }
        }


        launch(context = CoroutineName("bima")) {
            orderChannel.consumeEach { name ->
                delay(5000)
                val result = "\n Order $name by ${Thread.currentThread().name}"
                withContext(Dispatchers.Main) {
                    output_result.append(result)
                }
            }
        }

        launch(context = CoroutineName("ayu")) {
            orderChannel.consumeEach { name ->
                delay(3000)
                val result = "\n Order $name by ${Thread.currentThread().name}"
                withContext(Dispatchers.Main) {
                    output_result.append(result)
                }
            }
        }
    }
}
