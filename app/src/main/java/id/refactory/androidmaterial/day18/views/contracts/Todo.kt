package id.refactory.androidmaterial.day18.views.contracts

import id.refactory.androidmaterial.day18.models.TodoModel

interface Todo {
    interface View {
        fun onSuccessGetAllTodo(todo: List<TodoModel>)
        fun onSuccessUpdateTodo(todoModel: TodoModel)
        fun onSuccessDeleteTodo(id: Long)
        fun onSuccessInsertTodo(todoModel: TodoModel)
    }

    interface Presenter {
        fun getAllTodo()
        fun updateTodo(todoModel: TodoModel)
        fun insertTodo(todoModel: TodoModel)
        fun deleteTodo(todoModel: TodoModel)
    }
}