package id.refactory.androidmaterial.day18.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import id.refactory.androidmaterial.databinding.FragmentDetailTodoBinding
import id.refactory.androidmaterial.day18.models.TodoModel
import id.refactory.androidmaterial.day18.presenters.TodoPresenter
import id.refactory.androidmaterial.day18.repositories.TodoRepository
import id.refactory.androidmaterial.day18.repositories.local.TodoRoomRepository
import id.refactory.androidmaterial.day18.repositories.local.daos.TodoDao
import id.refactory.androidmaterial.day18.repositories.local.databases.LocalDatabase
import id.refactory.androidmaterial.day18.views.contracts.Todo

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailTodoFragment : Fragment(), Todo.View {
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

    private lateinit var binding: FragmentDetailTodoBinding

    private val args by navArgs<DetailTodoFragmentArgs>()
    private val dao: TodoDao by lazy { LocalDatabase.getDatabase(requireContext()).dao() }
    private val repository: TodoRepository by lazy { TodoRoomRepository(dao) }
    private val presenter: Todo.Presenter by lazy { TodoPresenter(this, repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTodoBinding.inflate(inflater, container, false).apply {
            tieTodo.setText(args.todo.task)
            cbStatus.isChecked = args.todo.status

            btnUpdate.setOnClickListener {
                if (tieTodo.text.toString().isNotEmpty()) {
                    presenter.updateTodo(
                        args.todo.copy(task = tieTodo.text.toString(), status = cbStatus.isChecked)
                    )
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailTodoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailTodoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSuccessGetAllTodo(todo: List<TodoModel>) {}

    override fun onSuccessUpdateTodo(todoModel: TodoModel) {
        requireActivity().runOnUiThread {
            Toast.makeText(
                requireContext(),
                "Todo dengan id = ${todoModel.id} berhasil diubah",
                Toast.LENGTH_SHORT
            ).show()
        }

        requireActivity().onBackPressed()
    }

    override fun onSuccessDeleteTodo(id: Long) {}

    override fun onSuccessInsertTodo(todoModel: TodoModel) {}
}