package id.refactory.androidmaterial.day17.presenters

import id.refactory.androidmaterial.day17.models.TodoModel
import id.refactory.androidmaterial.day17.repositories.TodoRepository
import id.refactory.androidmaterial.day17.views.contracts.Todo

class TodoPresenter(private val view: Todo.View, private val repository: TodoRepository) : Todo.Presenter {
    override fun getAllTodo() {
        val todoList by lazy { repository.getAllTodo() }
        view.onSuccessGetAllTodo(todoList)
    }

    override fun updateTodo(todoModel: TodoModel) {
        val todo by lazy { repository.updateTodo(todoModel) }
        view.onSuccessUpdateTodo(todo)
    }

    override fun insertTodo(task: String) {
        val todo by lazy { repository.insertTodo(task) }
        view.onSuccessInsertTodo(todo)
    }

    override fun deleteTodo(id: Long) {
        val todoId = repository.deleteTodo(id)
        view.onSuccessDeleteTodo(todoId)
    }
}