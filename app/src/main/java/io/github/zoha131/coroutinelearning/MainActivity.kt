package io.github.zoha131.coroutinelearning

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

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

    val mainViewModel by viewModels<MainViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.intLiveData.observe(this, Observer {
            textView.text = "Value from liveData: $it"
        })

        mainViewModel.strLiveData.observe(this, Observer {
            strTextView.text = "Value from emitSource: $it"
        })

    }

    override fun onStart() {
        super.onStart()
        Log.d("LifeCycleAware", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifeCycleAware", "onStop")
    }

    suspend fun differentDispatcher(): Int = withContext(Dispatchers.Default){
        for (i in 1..5){
            delay(2000)
            Log.d("LifeCycleAware", "inside Different Dispatcher")
        }
        return@withContext 9
    }

    override fun onResume() {
        super.onResume()

        Log.d("LifeCycleAware", "onResume")
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
