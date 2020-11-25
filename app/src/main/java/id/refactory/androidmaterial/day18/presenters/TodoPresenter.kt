package id.refactory.androidmaterial.day18.presenters

import id.refactory.androidmaterial.day18.models.TodoModel
import id.refactory.androidmaterial.day18.repositories.TodoRepository
import id.refactory.androidmaterial.day18.views.contracts.Todo
import java.util.concurrent.Executors

class TodoPresenter(private val view: Todo.View, private val repository: TodoRepository) :
    Todo.Presenter {

    private val executor by lazy { Executors.newFixedThreadPool(4) }

    override fun getAllTodo() {
        executor.execute {
            val todo = repository.getAllTodo()
            view.onSuccessGetAllTodo(todo)
        }
    }

    override fun updateTodo(todoModel: TodoModel) {
        executor.execute {
            val todo = repository.updateTodo(todoModel)
            view.onSuccessUpdateTodo(todo)
        }
    }

    override fun insertTodo(todoModel: TodoModel) {
        executor.execute {
            val todo = repository.insertTodo(todoModel)
            view.onSuccessInsertTodo(todo)
        }
    }

    override fun deleteTodo(todoModel: TodoModel) {
        executor.execute {
            val todoId = repository.deleteTodo(todoModel)
            view.onSuccessDeleteTodo(todoId)
        }
    }
}