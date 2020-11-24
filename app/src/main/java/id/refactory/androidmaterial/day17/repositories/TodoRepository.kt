package id.refactory.androidmaterial.day17.repositories

import id.refactory.androidmaterial.day17.models.TodoModel

interface TodoRepository {
    fun getAllTodo(): List<TodoModel>
    fun updateTodo(todoModel: TodoModel): TodoModel
    fun insertTodo(task: String): TodoModel
    fun deleteTodo(id: Long): Long
}