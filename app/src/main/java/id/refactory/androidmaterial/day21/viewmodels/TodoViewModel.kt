package id.refactory.androidmaterial.day21.viewmodels

import androidx.lifecycle.*
import id.refactory.androidmaterial.day21.models.TodoModel
import id.refactory.androidmaterial.day21.models.toEntity
import id.refactory.androidmaterial.day21.models.toRequest
import id.refactory.androidmaterial.day21.repositories.TodoLocalRepository
import id.refactory.androidmaterial.day21.repositories.TodoRemoteRepository
import id.refactory.androidmaterial.day21.repositories.local.entities.toModel
import id.refactory.androidmaterial.day21.repositories.remote.responses.toModel
import id.refactory.androidmaterial.day21.views.states.TodoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class TodoViewModelFactory(
    private val remoteRepository: TodoRemoteRepository,
    private val localRepository: TodoLocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(remoteRepository, localRepository) as T
    }
}

class TodoViewModel(
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
                val model = todoResponse.data.toModel()
                mutableState.postValue(TodoState.SuccessDeleteTodo(model))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    fun getAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoEntityList = localRepository.getAllTodo()
                val todoModelList = todoEntityList.asSequence().map { it.toModel() }.toList()
                mutableState.postValue(TodoState.SuccessGetAllFavorite(todoModelList))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    fun submitFavorite(todoModel: TodoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (localRepository.isFavorite(todoModel.id)) {
                    deleteFavorite(todoModel)
                } else {
                    insertFavorite(todoModel)
                }
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    private fun insertFavorite(todoModel: TodoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localRepository.insertTodo(todoModel.toEntity())
                mutableState.postValue(TodoState.SuccessInsertFavorite(todoModel))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    private fun deleteFavorite(todoModel: TodoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localRepository.deleteTodo(todoModel.toEntity())
                mutableState.postValue(TodoState.SuccessDeleteFavorite(todoModel))
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