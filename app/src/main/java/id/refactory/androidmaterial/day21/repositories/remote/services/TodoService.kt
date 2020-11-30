package id.refactory.androidmaterial.day21.repositories.remote.services

import id.refactory.androidmaterial.day21.repositories.remote.requests.TodoRequest
import id.refactory.androidmaterial.day21.repositories.remote.responses.BaseResponse
import id.refactory.androidmaterial.day21.repositories.remote.responses.TodoResponse
import retrofit2.http.*

interface TodoService {
    @GET("v1/todos")
    suspend fun getAllTodo(): BaseResponse<List<TodoResponse>>

    @POST("v1/todos")
    suspend fun insertTodo(@Body todoRequest: TodoRequest): BaseResponse<TodoResponse>

    @PUT("v1/todos/{id}")
    suspend fun updateTodoById(
        @Path("id") id: Long,
        @Body todoRequest: TodoRequest
    ): BaseResponse<TodoResponse>

    @DELETE("v1/todos/{id}")
    suspend fun deleteTodoById(@Path("id") id: Long): BaseResponse<TodoResponse>
}