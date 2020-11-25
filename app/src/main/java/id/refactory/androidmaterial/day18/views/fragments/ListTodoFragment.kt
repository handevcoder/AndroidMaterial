package id.refactory.androidmaterial.day18.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import id.refactory.androidmaterial.databinding.FragmentListTodoBinding
import id.refactory.androidmaterial.day18.models.TodoModel
import id.refactory.androidmaterial.day18.presenters.TodoPresenter
import id.refactory.androidmaterial.day18.repositories.TodoRepository
import id.refactory.androidmaterial.day18.repositories.local.TodoRoomRepository
import id.refactory.androidmaterial.day18.repositories.local.daos.TodoDao
import id.refactory.androidmaterial.day18.repositories.local.databases.LocalDatabase
import id.refactory.androidmaterial.day18.views.adapters.TodoAdapter
import id.refactory.androidmaterial.day18.views.contracts.Todo

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListTodoFragment : Fragment(), Todo.View, TodoAdapter.TodoListener {
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

    private lateinit var binding: FragmentListTodoBinding

    private val adapter by lazy { TodoAdapter(requireContext(), this) }
    private val dao: TodoDao by lazy { LocalDatabase.getDatabase(requireContext()).dao() }
    private val repository: TodoRepository by lazy { TodoRoomRepository(dao) }
    private val presenter: Todo.Presenter by lazy { TodoPresenter(this, repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListTodoBinding.inflate(inflater, container, false).apply {
            rvTodo.adapter = adapter

            btnAdd.setOnClickListener {
                if (tieTodo.text.toString().isNotEmpty()) {
                    val todo = TodoModel(id = 0, task = tieTodo.text.toString())
                    presenter.insertTodo(todo)
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
         * @return A new instance of fragment ListTodoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListTodoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSuccessGetAllTodo(todo: List<TodoModel>) {
        requireActivity().runOnUiThread {
            adapter.list = todo.toMutableList()
        }
    }

    override fun onSuccessUpdateTodo(todoModel: TodoModel) {
        requireActivity().runOnUiThread {
            adapter.updateTodo(todoModel)

            Toast.makeText(
                requireContext(),
                "Todo dengan id = ${todoModel.id} berhasil diubah",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSuccessDeleteTodo(id: Long) {
        requireActivity().runOnUiThread {
            adapter.deleteTodo(id)

            Toast.makeText(
                requireContext(),
                "Todo dengan id = $id berhasil dihapus",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSuccessInsertTodo(todoModel: TodoModel) {
        requireActivity().runOnUiThread {
            adapter.addTodo(todoModel)
        }

        binding.tieTodo.setText("")
    }

    override fun onClick(todoModel: TodoModel) {
        val action = ListTodoFragmentDirections.actionListTodoFragmentToDetailTodoFragment(todoModel)
        findNavController().navigate(action)
    }

    override fun onDelete(todoModel: TodoModel) {
        presenter.deleteTodo(todoModel)
    }

    override fun onChange(todoModel: TodoModel) {
        todoModel.status = !todoModel.status
        presenter.updateTodo(todoModel)
    }
}