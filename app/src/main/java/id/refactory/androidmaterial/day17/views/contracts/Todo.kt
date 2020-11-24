package id.refactory.androidmaterial.day17.views.contracts

import id.refactory.androidmaterial.day17.models.TodoModel

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
        fun insertTodo(task: String)
        fun deleteTodo(id: Long)
    }
}