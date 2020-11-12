package id.refactory.androidmaterial.day9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import id.refactory.androidmaterial.databinding.ActivityTodoBinding

class TodoActivity : AppCompatActivity(), TodoListener {

    private val binding by lazy { ActivityTodoBinding.inflate(layoutInflater) }
    private val adapter by lazy { TodoAdapter(this, this) }
    private val localStorage by lazy { LocalStorage(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            rvTodo.adapter = adapter
            btnAdd.setOnClickListener {
                if (tieTodo.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@TodoActivity,
                        "Todo tidak boleh kosong!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    localStorage.addTodo(tieTodo.text.toString())
                    adapter.addData(tieTodo.text.toString())

                    tieTodo.setText("")
                }
            }
        }

        adapter.setData(localStorage.getTodo())
    }

    override fun onClick(todo: String) {

    }

    override fun onDelete(todo: String) {
        val set = localStorage.getTodo().toMutableList().apply { remove(todo) }.toSet()
        localStorage.setTodo(set)
        adapter.deleteData(todo)
    }
}