package io.github.zoha131.coroutinelearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            logLater(2000)
        }
    }



    suspend fun logLater(millis: Long){
        Log.d("Coroutine", "Logging before suspend")
        delay(millis)
        Log.d("Coroutine", "Logging after suspend")
    }



}
