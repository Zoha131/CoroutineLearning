package io.github.zoha131.coroutinelearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = CoroutineExceptionHandler { context, throwable ->
            Log.d("LifecycleScope", "$throwable")
        }

        lifecycleScope.launch {

            launch(handler) { //This handler won't have any effect
                var value = 0

                while (true){
                    delay(1000)
                    Log.d("LifecycleScope", "From second coroutine : ${++value}")
                    errorTrial() // throw NumberFormatException("Exception Trial")
                }
            }

            lifecycleScope.launch(handler) { //This handler will handle exception
                var value = 0

                while (true){
                    delay(1000)
                    Log.d("LifecycleScope", "From second coroutine : ${++value}")
                    errorTrial() // throw NumberFormatException("Exception Trial")
                }
            }

        }

        lifecycleScope.launch(handler) {
            var value = 0

            while (true){
                delay(1000)
                Log.d("LifecycleScope", "From second coroutine : ${++value}")
                errorTrial() // throw NumberFormatException("Exception Trial")
            }
        }

    }


    suspend fun errorTrial(){
        Log.d("LifecycleScope", "Before Throwing Exception")
        delay(1000)
        throw NumberFormatException("Exception Trial")
    }

    //coroutine with concurrency
    suspend fun asyncTask(): Int = coroutineScope {

            Log.d("AsyncCoroutine", "Before calling")
            val a = async { logLater(5000) }
            val b = async { logLater(5000) }
            Log.d("AsyncCoroutine", "After calling")

            val c = a.await() + b.await()
            Log.d("AsyncCoroutine", "Work completes here")

            c
        }

    //coroutine without concurrency
    /*suspend fun asyncTask(): Int {

        Log.d("AsyncCoroutine", "Before calling")
        val a = logLater(5000)
        val b =  logLater(5000)
        Log.d("AsyncCoroutine", "After calling")

        val c = a + b
        Log.d("AsyncCoroutine", "Work completes here")

        return c
    }*/

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
