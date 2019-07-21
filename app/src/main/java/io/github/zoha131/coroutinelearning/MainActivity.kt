package io.github.zoha131.coroutinelearning

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* val handler = CoroutineExceptionHandler { context, throwable ->
            Log.d("LifecycleScope", "$throwable")
        }

        lifecycleScope.launch {

            val result: Deferred<Unit> = async {
                delay(1000)
                Log.d("LifecycleScope", "From second coroutine")
                errorTrial() // throw NumberFormatException("Exception Trial")
            }

            try {
                result.await()
            } catch (throwable: NumberFormatException){
                Log.d("LifecycleScope", "$throwable")
            }

        }

        lifecycleScope.launch(handler) {
            var value = 0

            while (true) {
                delay(1000)
                Log.d("LifecycleScope", "From second coroutine : ${++value}")
                errorTrial() // throw NumberFormatException("Exception Trial")
            }
        }*/

        lifecycleScope.launchWhenStarted {
            //Any code in this block will be suspended if
            //the lifecycle state less than Started
            delay(2000)
            Log.d("LifeCycleAware", "launchWhenStarted")
        }

    }

    override fun onStart() {
        super.onStart()

        Log.d("LifeCycleAware", "onStart")

    }

    override fun onResume() {
        super.onResume()

        Log.d("LifeCyclseAware", "onResume")
    }


    suspend fun errorTrial() {
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
