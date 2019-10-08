package com.rakaadinugroho.coroutinesplayground.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rakaadinugroho.coroutinesplayground.R
import com.rakaadinugroho.coroutinesplayground.ui.queue.QueueActivity
import com.rakaadinugroho.coroutinesplayground.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_sample_basic.setOnClickListener {
            this.goTo(QueueActivity())
        }

        button_sample_jetpack.setOnClickListener {
            this.goTo(SearchActivity())
        }
    }

    fun Activity.goTo(destination: Activity) {
        val intent = Intent(this, destination::class.java)
        startActivity(intent)
    }
}
