package id.refactory.androidmaterial.day21.repositories.remote

import id.refactory.androidmaterial.day21.repositories.TodoRemoteRepository
import id.refactory.androidmaterial.day21.repositories.remote.requests.TodoRequest
import id.refactory.androidmaterial.day21.repositories.remote.responses.BaseResponse
import id.refactory.androidmaterial.day21.repositories.remote.responses.TodoResponse
import id.refactory.androidmaterial.day21.repositories.remote.services.TodoService

class TodoRemoteRepository(private val service: TodoService) : TodoRemoteRepository {
    override suspend fun getAllTodo(): BaseResponse<List<TodoResponse>> = service.getAllTodo()

    override suspend fun insertTodo(todoRequest: TodoRequest): BaseResponse<TodoResponse> =
        service.insertTodo(todoRequest)

    override suspend fun updateTodo(
        id: Long,
        todoRequest: TodoRequest
    ): BaseResponse<TodoResponse> = service.updateTodoById(id, todoRequest)

    override suspend fun deleteTodo(id: Long): BaseResponse<String> = service.deleteTodoById(id)
}