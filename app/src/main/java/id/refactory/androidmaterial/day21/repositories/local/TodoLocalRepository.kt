package id.refactory.androidmaterial.day21.repositories.local

import id.refactory.androidmaterial.day21.repositories.TodoLocalRepository
import id.refactory.androidmaterial.day21.repositories.local.daos.TodoDao
import id.refactory.androidmaterial.day21.repositories.local.entities.TodoEntity

class TodoLocalRepository(private val dao: TodoDao): TodoLocalRepository {
    override suspend fun getAllTodo(): List<TodoEntity> = dao.getAllTodo()
    override suspend fun insertTodo(todoEntity: TodoEntity) = dao.insertTodo(todoEntity)
    override suspend fun deleteTodo(todoEntity: TodoEntity) = dao.deleteTodo(todoEntity)
}