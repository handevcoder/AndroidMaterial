package id.refactory.androidmaterial.day21.repositories

import id.refactory.androidmaterial.day21.repositories.remote.requests.TodoRequest
import id.refactory.androidmaterial.day21.repositories.remote.responses.BaseResponse
import id.refactory.androidmaterial.day21.repositories.remote.responses.TodoResponse

interface TodoRemoteRepository {
    suspend fun getAllTodo(): BaseResponse<List<TodoResponse>>
    suspend fun insertTodo(todoRequest: TodoRequest): BaseResponse<TodoResponse>
    suspend fun updateTodo(id: Long, todoRequest: TodoRequest): BaseResponse<TodoResponse>
    suspend fun deleteTodo(id: Long): BaseResponse<String>
}