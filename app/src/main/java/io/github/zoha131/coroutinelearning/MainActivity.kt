package io.github.zoha131.coroutinelearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {

            Log.d("Coroutine", "Logging from coroutine. ${Thread.currentThread().name}")

            asyncTask()
        }
    }

    suspend fun asyncTask(): Int = coroutineScope {

            Log.d("AsyncCoroutine", "Before calling")
            val a = async { logLater(5000) }
            val b = async { logLater(5000) }
            Log.d("AsyncCoroutine", "After calling")

            val c = a.await() + b.await()
            Log.d("AsyncCoroutine", "Work completes here")

            c
        }


    suspend fun logLater(millis: Long): Int {

        Log.d("Coroutine", "Logging outside of withcontext. ${Thread.currentThread().name}")

        val result = withContext(Dispatchers.Default) {

            Log.d("Coroutine", "Logging before suspend. ${Thread.currentThread().name}")
            delay(millis)
            Log.d("Coroutine", "Logging after suspend. ${Thread.currentThread().name}")

            9
        }

        return result
    }


}
