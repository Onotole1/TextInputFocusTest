package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private val root = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch {
            withContext(Dispatchers.IO) {
                delay(4000)
            }

            textInput.error = "Test"
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        root.cancel()
    }
}
