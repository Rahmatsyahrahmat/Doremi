package rahmatsyah.doremi.app.ui.splash


import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel:ViewModel() {
    private val isFinish:MutableLiveData<Boolean> = MutableLiveData(false)
    init {
        viewModelScope.launch {
            delay(1500)
            isFinish.value = true
        }
    }
    fun isFinish() = isFinish
}