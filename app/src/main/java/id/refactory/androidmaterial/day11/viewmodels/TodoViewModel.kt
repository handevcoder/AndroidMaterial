package id.refactory.androidmaterial.day11.viewmodels

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import id.refactory.androidmaterial.day11.storages.LocalStorage

sealed class State {
    data class Loading(val message: String = "Loading...") : State()
    data class Success(val data: Set<String>) : State()
    data class Error(val message: String = "Oops something went wrong!") : State()
}

class TodoViewModel(private val app: Application) : AndroidViewModel(app) {
    private val localStorage by lazy { LocalStorage(app.applicationContext) }

    private val mutableTodoState by lazy { MutableLiveData<State>() }
    val todoSate get() = mutableTodoState

    fun getTodo() {
        mutableTodoState.value = State.Loading()

        Handler(Looper.getMainLooper()).postDelayed({
            val data = localStorage.getTodo()
            mutableTodoState.value = State.Success(data)
        }, 1000)
    }
}