package id.refactory.androidmaterial.day18.repositories.local

import id.refactory.androidmaterial.day18.models.TodoModel
import id.refactory.androidmaterial.day18.models.toEntity
import id.refactory.androidmaterial.day18.repositories.TodoRepository
import id.refactory.androidmaterial.day18.repositories.local.daos.TodoDao
import id.refactory.androidmaterial.day18.repositories.local.databases.LocalDatabase
import id.refactory.androidmaterial.day18.repositories.local.entities.toModel

class TodoRoomRepository(private val dao: TodoDao): TodoRepository {

    override fun getAllTodo(): List<TodoModel> {
        return dao.getAllTodo().asSequence().map { it.toModel() }.toList()
    }

    override fun updateTodo(todoModel: TodoModel): TodoModel {
        dao.updateTodo(todoModel.toEntity())
        return todoModel
    }

    override fun insertTodo(todoModel: TodoModel): TodoModel {
        val todo = todoModel.toEntity()
        val id = dao.createTodo(todo)
        return todoModel.copy(id = id)
    }

    override fun deleteTodo(todoModel: TodoModel): Long {
        dao.deleteTodo(todoModel.toEntity())
        return todoModel.id
    }
}