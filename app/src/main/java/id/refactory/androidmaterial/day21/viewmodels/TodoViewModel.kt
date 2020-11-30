package id.refactory.androidmaterial.day21.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.refactory.androidmaterial.day21.models.TodoModel
import id.refactory.androidmaterial.day21.models.toRequest
import id.refactory.androidmaterial.day21.repositories.TodoLocalRepository
import id.refactory.androidmaterial.day21.repositories.TodoRemoteRepository
import id.refactory.androidmaterial.day21.repositories.remote.responses.toModel
import id.refactory.androidmaterial.day21.views.states.TodoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(
    private val context: Context,
    private val remoteRepository: TodoRemoteRepository,
    private val localRepository: TodoLocalRepository
) : ViewModel() {
    private val mutableState by lazy { MutableLiveData<TodoState>() }
    val state: LiveData<TodoState> get() = mutableState

    fun getAllTodo() {
        mutableState.value = TodoState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoResponse = remoteRepository.getAllTodo()
                val todoListModel = todoResponse.data.asSequence().map { it.toModel() }.toList()
                mutableState.postValue(TodoState.SuccessGetAllTodo(todoListModel))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    fun insertTodo(todoModel: TodoModel) {
        mutableState.value = TodoState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoResponse = remoteRepository.insertTodo(todoModel.toRequest())
                val todo = todoResponse.data.toModel()
                mutableState.postValue(TodoState.SuccessInsertTodo(todo))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    fun updateTodo(todoModel: TodoModel) {
        mutableState.value = TodoState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoResponse = remoteRepository.updateTodo(todoModel.id, todoModel.toRequest())
                val todo = todoResponse.data.toModel()
                mutableState.postValue(TodoState.SuccessUpdateTodo(todo))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    fun deleteTodo(todoModel: TodoModel) {
        mutableState.value = TodoState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoResponse = remoteRepository.deleteTodo(todoModel.id)
                val message = todoResponse.data
                mutableState.postValue(TodoState.SuccessDeleteTodo(message))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    private fun onError(exc: Exception) {
        exc.printStackTrace()
        mutableState.postValue(TodoState.Error(exc))
    }
}