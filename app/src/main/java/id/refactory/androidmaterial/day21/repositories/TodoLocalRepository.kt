package id.refactory.androidmaterial.day21.repositories

import id.refactory.androidmaterial.day21.repositories.local.entities.TodoEntity

interface TodoLocalRepository {
    suspend fun getAllTodo(): List<TodoEntity>
    suspend fun insertTodo(todoEntity: TodoEntity)
    suspend fun deleteTodo(todoEntity: TodoEntity)
}