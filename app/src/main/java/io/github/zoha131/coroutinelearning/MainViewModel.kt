package io.github.zoha131.coroutinelearning

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    val mainRepository = MainRepository() // normally this would come from DI

    private val _intLiveData = MutableLiveData<Int>()
    val intLiveData: LiveData<Int>
        get() = _intLiveData

    init {
        viewModelScope.launch {
            // Coroutine that will be canceled when
            // the ViewModel is cleared.
            val data = mainRepository.getData()
            _intLiveData.value = data
        }
    }


}

class MainRepository{
    suspend fun getData(): Int {
        delay(1000)
        return 6
    }
}

