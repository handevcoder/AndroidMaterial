package id.refactory.androidmaterial.day18.repositories

import id.refactory.androidmaterial.day18.models.TodoModel

interface TodoRepository {
    fun getAllTodo(): List<TodoModel>
    fun updateTodo(todoModel: TodoModel): TodoModel
    fun insertTodo(todoModel: TodoModel): TodoModel
    fun deleteTodo(todoModel: TodoModel): Long
}