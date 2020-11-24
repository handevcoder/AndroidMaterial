package id.refactory.androidmaterial.day17.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.ItemTodoNewBinding
import id.refactory.androidmaterial.day17.models.TodoModel

class TodoAdapter(
    private val context: Context, private val listener: TodoListener
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    var list = mutableListOf<TodoModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addTodo(todoModel: TodoModel) {
        list.add(0, todoModel)
        notifyItemInserted(0)
    }

    fun deleteTodo(id: Long) {
        val index = list.indexOfFirst { it.id == id }
        if (index != -1) {
            list.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateTodo(todoModel: TodoModel) {
        val index = list.indexOfFirst { it.id == todoModel.id }
        if (index != -1) {
            list[index] = todoModel
            notifyItemChanged(index)
        }
    }

    interface TodoListener {
        fun onClick(todoModel: TodoModel)
        fun onDelete(id: Long)
        fun onChange(todoModel: TodoModel)
    }

    inner class ViewHolder(
        private val binding: ItemTodoNewBinding,
        private val listener: TodoListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(todoModel: TodoModel) {
            binding.run {
                tvTodo.text = todoModel.task

                root.setOnClickListener { listener.onClick(todoModel) }
                ivTodo.setOnClickListener { listener.onDelete(todoModel.id) }
                ivStatus.setImageResource(if (todoModel.status) R.drawable.ic_done else R.drawable.ic_todo)
                ivStatus.setOnClickListener { listener.onChange(todoModel) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        return ViewHolder(
            ItemTodoNewBinding.inflate(LayoutInflater.from(context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size
}