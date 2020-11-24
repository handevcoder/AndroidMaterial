package id.refactory.androidmaterial.day17.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import id.refactory.androidmaterial.R
import id.refactory.androidmaterial.databinding.FragmentTodoListBinding
import id.refactory.androidmaterial.day17.models.TodoModel
import id.refactory.androidmaterial.day17.presenters.TodoPresenter
import id.refactory.androidmaterial.day17.repositories.TodoRepository
import id.refactory.androidmaterial.day17.repositories.local.TodoLocalRepository
import id.refactory.androidmaterial.day17.views.adapters.TodoAdapter
import id.refactory.androidmaterial.day17.views.contracts.Todo

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TodoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoListFragment : Fragment(), Todo.View, TodoAdapter.TodoListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentTodoListBinding
    private val adapter by lazy { TodoAdapter(requireContext(), this) }
    private val repository: TodoRepository by lazy { TodoLocalRepository(requireContext()) }
    private val presenter: Todo.Presenter by lazy { TodoPresenter(this, repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false).apply {
            rvTodo.adapter = adapter

            btnAdd.setOnClickListener {
                if (tieTodo.text.toString().isNotEmpty()) {
                    presenter.insertTodo(tieTodo.text.toString())
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Task tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        presenter.getAllTodo()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TodoListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodoListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSuccessGetAllTodo(todo: List<TodoModel>) {
        adapter.list = todo.toMutableList()
    }

    override fun onSuccessUpdateTodo(todoModel: TodoModel) {
        adapter.updateTodo(todoModel)
    }

    override fun onSuccessDeleteTodo(id: Long) {
        adapter.deleteTodo(id)

        Toast.makeText(
            requireContext(),
            "Todo dengan id = $id berhasil dihapus",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onSuccessInsertTodo(todoModel: TodoModel) {
        adapter.addTodo(todoModel)

        binding.tieTodo.setText("")
    }

    override fun onClick(todoModel: TodoModel) {
        val action =
            TodoListFragmentDirections.actionTodoListFragmentToTodoDetailFragment2(todoModel)
        findNavController().navigate(action)
    }

    override fun onDelete(id: Long) {
        presenter.deleteTodo(id)
    }

    override fun onChange(todoModel: TodoModel) {
        todoModel.status = !todoModel.status
        presenter.updateTodo(todoModel)
    }
}