package id.refactory.androidmaterial.day21.views.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import id.refactory.androidmaterial.day21.models.TodoModel

class TodoAdapter(val context: Context, val listener: TodoListener) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    interface TodoListener {
        fun onChange(todoModel: TodoModel)
        fun onDelete(todoModel: TodoModel)
        fun onFavorite(todoModel: TodoModel)
    }

    inner class ViewHolder {

    }
}